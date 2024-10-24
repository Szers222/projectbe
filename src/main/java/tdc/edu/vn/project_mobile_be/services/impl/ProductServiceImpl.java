package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.ProductSpecifications;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.NumberErrorException;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductImageResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductSizeResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductSupplierResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.category.CategoryResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.post.PostResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.status.PostStatus;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.*;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl extends AbService<Product, UUID> implements ProductService {

    public final int PRODUCT_CLOTHES = 0;
    public final int PRODUCT_SHOES = 1;
    public final int PRODUCT_ACCESSORIES = 2;


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductSupplierRepository productSupplierRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostStatusRepository postStatusRepository;

    @Override
    public Product createProduct(ProductCreateRequestDTO params) {
        LocalDateTime releaseDateTime;

        // Kiểm tra nếu postRelease là null, dùng thời gian hiện tại
        if (params.getPostDTO().getPostRelease() == null) {
            releaseDateTime = LocalDateTime.now();
        } else {
            // Nếu có, dùng ngày phát hành được cung cấp, đặt vào đầu ngày
            releaseDateTime = params.getPostDTO().getPostRelease().atStartOfDay();
        }

        // Chuyển đổi sang Timestamp
        Timestamp releaseTimestamp = Timestamp.valueOf(releaseDateTime);

        PostStatus status = postStatusRepository.findByPostStatusId(params.getPostDTO().getPostStatusId());


        // Tạo đối tượng Post mới
        Post post = new Post();
        post.setPostId(UUID.randomUUID());
        post.setPostRelease(releaseTimestamp);
        post.setPostName(params.getPostDTO().getPostName());
        post.setPostContent(params.getPostDTO().getPostContent());
        post.setPostStatus(status);
        // Lưu post vào cơ sở dữ liệu
        Post savedPost = postRepository.save(post);

        // Tạo đối tượng Product mới
        Product product = new Product();
        product.setProductId(UUID.randomUUID()); // Tạo UUID cho product
        product.setProductName(params.getProductName()); // Đặt tên product
        product.setProductPrice(params.getProductPrice()); // Đặt giá
        product.setProductQuantity(params.getProductQuantity()); // Đặt số lượng
        product.setProductYearOfManufacture(params.getProductYearOfManufacture()); // Đặt năm sản xuất

        // Liên kết product với post vừa tạo
        product.setPost(savedPost);

        // Lưu product vào cơ sở dữ liệu
        Product savedProduct = productRepository.save(product);
        // Trả về product đã lưu
        return savedProduct;
    }

    @Override
    public Page<ProductResponseDTO> findProductsByFilters(ProductRequestParamsDTO params, Pageable pageable) {
        Specification<Product> spec = Specification.where(null);  // Khởi tạo Specification rỗng

        // Lọc theo danh mục (category)
//        if (params.getCategoryId() != null && categoryRepository.findById(params.getCategoryId()).isPresent()) {
//            spec = spec.and(ProductSpecifications.hasCategory(params.getCategoryId()));
//        }

        // Lọc theo khoảng giá
        if (params.getMinPrice() != null && params.getMaxPrice() != null) {
            if (this.validatePriceRange(params.getMinPrice(), params.getMaxPrice()) == false) {
                spec = spec.and(ProductSpecifications.priceBetween(params.getMinPrice(), params.getMaxPrice()));
            }
        }

        // Lọc theo kích thước
        if (params.getSizeIds() != null && !params.getSizeIds().isEmpty()) {
            spec = spec.and(ProductSpecifications.hasSizes(params.getSizeIds()));
        }

        // Lọc theo nhà cung cấp (supplier)
        if (params.getSupplierIds() != null && !params.getSupplierIds().isEmpty()) {
            spec = spec.and(ProductSpecifications.hasSupplier(params.getSupplierIds()));
        }

        if (params.getDirection() != null && params.getSort() != null) {
            spec = spec.and(ProductSpecifications.hasSort(params.getSort(), params.getDirection()));
        }

        if (params.getSearch() != null) {
            spec = spec.and(ProductSpecifications.hasSearch(params.getSearch()));
        } else {
            spec = spec.and(ProductSpecifications.hasSearch(""));
        }

        // Truy vấn với các tiêu chí kết hợp
        Page<Product> products = productRepository.findAll(spec, pageable);
        if (products.isEmpty() || products.getSize() == 0) {
            throw new ListNotFoundException("Không tìm thấy sản phẩm");
        }

        // Chuyển đổi sang DTO
        return products.map(product -> {
            double price = product.getProductPrice();
            if (price < 0) {
                throw new NumberErrorException("Price must be greater than 0");
            }

            List<CategoryResponseDTO> categoryResponseDTOs = convertToDTOList(
                    product.getCategories() != null ?
                            product.getCategories().stream().collect(Collectors.toList())
                            : Collections.emptyList(),
                    category -> {
                        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
                        categoryResponseDTO.toDto(category);
                        return categoryResponseDTO;
                    }
            );

            List<ProductImageResponseDTO> productImageResponseDTOS = convertToDTOList(
                    product.getImages() != null ?
                            product.getImages().stream().collect(Collectors.toList())
                            : Collections.emptyList(),
                    productImage -> {
                        ProductImageResponseDTO productImageResponseDTO = new ProductImageResponseDTO();
                        productImageResponseDTO.toDto(productImage);
                        return productImageResponseDTO;
                    }
            );

            List<ProductSizeResponseDTO> productSizeResponseDTOS = convertToDTOList(
                    product.getSizes() != null ?
                            product.getSizes().stream().collect(Collectors.toList())
                            : Collections.emptyList(),
                    productSize -> {
                        ProductSizeResponseDTO productSizeResponseDTO = new ProductSizeResponseDTO();
                        productSizeResponseDTO.toDto(productSize);
                        return productSizeResponseDTO;
                    }
            );

            ProductSupplierResponseDTO productSupplierResponseDTO = new ProductSupplierResponseDTO();
            if (product.getSupplier() != null) {
                productSupplierResponseDTO.toDto(product.getSupplier());
            }

            PostResponseDTO postResponseDTO = new PostResponseDTO();
            if (product.getPost() != null) {
                postResponseDTO.toDto(product.getPost());
            }

            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(product);
            dto.setProductPrice(formatPrice(product.getProductPrice()));
            dto.setProductPriceSale(formatPrice(price - (price * dto.getProductSale() / 100)));
            dto.setCategoryResponseDTO(categoryResponseDTOs);
            dto.setProductSizeResponseDTOs(productSizeResponseDTOS);
            dto.setSupplier(productSupplierResponseDTO);
            dto.setPostResponseDTO(postResponseDTO);
            dto.setProductImageResponseDTOs(productImageResponseDTOS);
            return dto;
        });

    }

    @Override
    public boolean deleteProduct(UUID productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new EntityNotFoundException("Product không tồn tại !");
        }
        Product product = productOptional.get();
        productRepository.delete(product);
        return true;
    }

    @Override
    public Product updateProduct(ProductUpdateRequestDTO params, UUID productId) {
        LocalDateTime releaseDateTime;

        // Kiểm tra nếu postRelease là null, dùng thời gian hiện tại
        if (params.getPostDTO().getPostRelease() == null) {
            releaseDateTime = LocalDateTime.now();
        } else {
            // Nếu có, dùng ngày phát hành được cung cấp, đặt vào đầu ngày
            releaseDateTime = params.getPostDTO().getPostRelease().atStartOfDay();
        }

        // Chuyển đổi sang Timestamp
        Timestamp releaseTimestamp = Timestamp.valueOf(releaseDateTime);

        PostStatus status = postStatusRepository.findByPostStatusId(params.getPostDTO().getPostStatusId());


        // Tạo đối tượng Post mới
        Post post = new Post();
        post.setPostId(UUID.randomUUID());
        post.setPostRelease(releaseTimestamp);
        post.setPostName(params.getPostDTO().getPostName());
        post.setPostContent(params.getPostDTO().getPostContent());
        post.setPostStatus(status);
        // Lưu post vào cơ sở dữ liệu
        Post savedPost = postRepository.save(post);

        // Tạo đối tượng Product mới
        Product product = new Product();
        product.setProductId(UUID.randomUUID()); // Tạo UUID cho product
        product.setProductName(params.getProductName()); // Đặt tên product
        product.setProductPrice(params.getProductPrice()); // Đặt giá
        product.setProductQuantity(params.getProductQuantity()); // Đặt số lượng
        product.setProductYearOfManufacture(params.getProductYearOfManufacture()); // Đặt năm sản xuất

        // Liên kết product với post vừa tạo
        product.setPost(savedPost);

        // Lưu product vào cơ sở dữ liệu
        Product savedProduct = productRepository.save(product);
        // Trả về product đã lưu
        return savedProduct;
    }

    public String formatPrice(double price) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(0);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(price);
    }

    private boolean validatePriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice.compareTo(maxPrice) > 0) {
            throw new NumberErrorException("Min price must be less than max price");
        }
        if (minPrice.compareTo(BigDecimal.ZERO) < 0 || maxPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new NumberErrorException("Min price and max price must be greater than 0");
        }
        if (minPrice.compareTo(BigDecimal.ZERO) == 0 && maxPrice.compareTo(BigDecimal.ZERO) == 0) {
            throw new NumberErrorException("Min price and max price must not be equal to 0");
        }
        if (maxPrice.compareTo(BigDecimal.valueOf(2000000)) > 0) {
            throw new NumberErrorException("Max price must not be greater than 2 million");
        }
        return true;
    }

    private <E, D> List<D> convertToDTOList(List<E> entities, Function<E, D> converter) {
        return entities.stream().map(converter).collect(Collectors.toList());
    }
}
