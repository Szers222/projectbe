package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.commond.ProductSpecifications;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.NumberErrorException;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.category.CategoryResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.post.PostResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.*;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;
import tdc.edu.vn.project_mobile_be.entities.status.PostStatus;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CategoryRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.PostStatusRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.SizeProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.CouponService;
import tdc.edu.vn.project_mobile_be.interfaces.service.PostService;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductImageService;
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

    private final int COUPON_PER_HUNDRED_TYPE = 0;
    private final int COUPON_PRICE_TYPE = 1;
    private final int PRODUCT_DEFAULT_SIZE = 0;
    private final int PRODUCT_RELATE_SIZE = 6;
    private final int PRODUCT_MIN_PRICE = 0;
    private final int PRODUCT_MIN_SIZE = 0;
    private final double SOLVE_SALE = 1;
    private final double MAX_PER = 100;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private PostStatusRepository postStatusRepository;
    @Autowired
    private SizeProductRepository sizeProductRepository;
    @Autowired
    private CouponService couponService;
    @Autowired
    private ProductImageService productImageService;

    @Override
    @Transactional
    public Product createProduct(ProductCreateRequestDTO params, MultipartFile[] files) {
        LocalDateTime releaseDateTime;

        // Kiểm tra nếu postRelease là null, dùng thời gian hiện tại
        if (params.getPost().getPostRelease() == null) {
            releaseDateTime = LocalDateTime.now();
        } else {
            // Nếu có, dùng ngày phát hành được cung cấp, đặt vào đầu ngày
            releaseDateTime = params.getPost().getPostRelease().atStartOfDay();
        }

        // Chuyển đổi sang Timestamp
        Timestamp releaseTimestamp = Timestamp.valueOf(releaseDateTime);

        PostStatus status = postStatusRepository.findByPostStatusId(params.getPost().getPostStatusId());


        // Tạo đối tượng Post mới
        Post post = postService.createPost(params.getPost());

        Coupon coupon = couponService.createCoupon(params.getCoupon());

        Set<Category> categories = new HashSet<>();

        for (UUID categoryId : params.getCategoryId()) {
            Category category = categoryRepository.findByCategoryId(categoryId);
            if (category == null) {
                throw new EntityNotFoundException("Category not found for ID: " + categoryId);
            }
            categories.add(category);
        }
        double productSale = solveProductSale(params.getProductPrice(), coupon);


        // Tạo đối tượng Product mới
        Product product = params.toEntity();
        product.setProductId(UUID.randomUUID());
        product.setCategories(categories);
        product.setPost(post);
        product.setCoupon(coupon);
        product.setProductSale(productSale);
        Product savedProduct = productRepository.save(product);

        Set<ProductImage> productImages = productImageService.createProductImageWithProduct(
                    params.getProductImageResponseDTOs(),
                    savedProduct.getProductId(),
                files);
        savedProduct.setImages(productImages);

        return savedProduct;
    }

    @Override
    @Transactional
    public Product updateProduct(ProductUpdateRequestDTO params, UUID productId) {
        LocalDateTime releaseDateTime;

        // Kiểm tra nếu postRelease là null, dùng thời gian hiện tại
        if (params.getPost().getPostRelease() == null) {
            releaseDateTime = LocalDateTime.now();
        } else {
            // Nếu có, dùng ngày phát hành được cung cấp, đặt vào đầu ngày
            releaseDateTime = params.getPost().getPostRelease().atStartOfDay();
        }

        // Chuyển đổi sang Timestamp
        Timestamp releaseTimestamp = Timestamp.valueOf(releaseDateTime);

        PostStatus status = postStatusRepository.findByPostStatusId(params.getPost().getPostStatusId());


        Post post = postService.updatePostByProductId(params.getPost(), productId);
        if (post == null) {
            throw new jakarta.persistence.EntityNotFoundException("Post không tồn tại !");
        }
        Coupon coupon = couponService.updateCouponByProductId(params.getCoupon(), productId);
        if (coupon == null) {
            throw new jakarta.persistence.EntityNotFoundException("Coupon không tồn tại !");
        }
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("Product không tồn tại !");
        }
        Set<Category> categories = new HashSet<>();

        for (UUID categoryId : params.getCategoryId()) {
            Category category = categoryRepository.findByCategoryId(categoryId);
            categories.add(category);
        }
        double productSale = solveProductSale(params.getProductPrice(), coupon);
        Product product = optionalProduct.get();
        product.setProductName(params.getProductName());
        product.setProductPrice(params.getProductPrice());
        product.setProductQuantity(params.getProductQuantity());
        product.setProductYearOfManufacture(params.getProductYearOfManufacture());
        product.setCoupon(coupon);
        product.setPost(post);
        product.setCategories(categories);
        product.setProductSale(productSale);

        return productRepository.save(product);
    }

    @Override
    public Page<ProductResponseDTO> findProductRelate(UUID categoryId, Pageable pageable) {
        Page<Product> productPage = productRepository.findByIdWithCategories(categoryId, pageable);

        if (productPage.getContent().size() < PRODUCT_RELATE_SIZE || (productPage.isEmpty())) {
            List<Product> products = new ArrayList<>(productPage.getContent());
            List<Product> allProducts = productRepository.findAll();
            for (int i = 0; i < allProducts.size(); i++) {
                if (products.size() == PRODUCT_RELATE_SIZE) {
                    break;
                }
                int addRandomProduct = new Random().nextInt(allProducts.size());
                Product product = allProducts.get(addRandomProduct);
                System.console().printf("IntproductAdd: %s", addRandomProduct);
                if (!products.contains(product)) {
                    products.add(product);
                }
                productPage = new PageImpl<>(products, pageable, products.size());
            }
        }
        List<Product> sortProducts = new ArrayList<>(productPage.getContent());
        sortProducts.sort(Comparator.comparing(Product::getProductPriceSale));

        Page<Product> productPageRandom = new PageImpl<>(sortProducts.subList(PRODUCT_DEFAULT_SIZE, PRODUCT_RELATE_SIZE));

        return productPageRandom.map(product -> {
            List<CategoryResponseDTO> categoryResponseDTOs = getCategoryResponseDTOs(product);
            List<ProductImageResponseDTO> productImageResponseDTOS = getProductImageResponseDTOs(product);
            List<ProductSizeResponseDTO> productSizeResponseDTOS = getProductSizeResponseDTOs(product);
            ProductSupplierResponseDTO productSupplierResponseDTO = getProductSupplierResponseDTO(product);
            PostResponseDTO postResponseDTO = getPostResponseDTO(product);
            String productPriceSaleString = formatProductPriceSale(product);
            String productPriceString = formatProductPrice(product);


            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(product);
            dto.setProductPrice(productPriceString);
            dto.setProductPriceSale(productPriceSaleString);
            dto.setCategoryResponseDTO(categoryResponseDTOs);
            dto.setProductSizeResponseDTOs(productSizeResponseDTOS);
            dto.setSupplier(productSupplierResponseDTO);
            dto.setPostResponseDTO(postResponseDTO);
            dto.setProductImageResponseDTOs(productImageResponseDTOS);
            return dto;
        });

    }

    @Override
    public Page<ProductResponseDTO> findProductsByFilters(ProductRequestParamsDTO params, Pageable pageable) {
        Specification<Product> spec = Specification.where(null);  // Khởi tạo Specification rỗng
        // Lọc theo danh mục (category)
        if (params.getCategoryId() != null && categoryRepository.findById(params.getCategoryId()).isPresent()) {
            spec = spec.and(ProductSpecifications.hasCategory(params.getCategoryId()));
        }

        // Lọc theo khoảng giá
        if (params.getMinPrice() != null && params.getMaxPrice() != null) {
            if (this.validatePriceRange(params.getMinPrice(), params.getMaxPrice())) {
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
        if (products.isEmpty() || products.getSize() == PRODUCT_MIN_SIZE) {
            throw new ListNotFoundException("Không tìm thấy sản phẩm");
        }

        // Chuyển đổi sang DTO
        return products.map(product -> {
            List<CategoryResponseDTO> categoryResponseDTOs = getCategoryResponseDTOs(product);
            List<ProductImageResponseDTO> productImageResponseDTOS = getProductImageResponseDTOs(product);
            List<ProductSizeResponseDTO> productSizeResponseDTOS = getProductSizeResponseDTOs(product);
            ProductSupplierResponseDTO productSupplierResponseDTO = getProductSupplierResponseDTO(product);
            PostResponseDTO postResponseDTO = getPostResponseDTO(product);
            String productPriceSaleString = formatProductPriceSale(product);
            String productPriceString = formatProductPrice(product);

            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(product);
            dto.setProductPrice(productPriceString);
            dto.setCategoryResponseDTO(categoryResponseDTOs);
            dto.setProductSizeResponseDTOs(productSizeResponseDTOS);
            dto.setSupplier(productSupplierResponseDTO);
            dto.setPostResponseDTO(postResponseDTO);
            dto.setProductImageResponseDTOs(productImageResponseDTOS);
            dto.setProductPriceSale(productPriceSaleString);
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
    public Page<ProductResponseDTO> getProductByCategoryId(UUID categoryId, Pageable pageable) {

        Page<Product> products = productRepository.findByCategoryId(categoryId, pageable);
        if (products.isEmpty() || products.getSize() == PRODUCT_MIN_SIZE) {
            throw new ListNotFoundException("Không tìm thấy sản phẩm");
        }

        return products.map(product -> {
            List<CategoryResponseDTO> categoryResponseDTOs = getCategoryResponseDTOs(product);
            List<ProductImageResponseDTO> productImageResponseDTOS = getProductImageResponseDTOs(product);
            List<ProductSizeResponseDTO> productSizeResponseDTOS = getProductSizeResponseDTOs(product);
            ProductSupplierResponseDTO productSupplierResponseDTO = getProductSupplierResponseDTO(product);
            PostResponseDTO postResponseDTO = getPostResponseDTO(product);
            String productPriceSaleString = formatProductPriceSale(product);
            String productPriceString = formatProductPrice(product);

            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(product);
            dto.setProductPrice(productPriceString);
            dto.setProductPriceSale(productPriceSaleString);
            dto.setCategoryResponseDTO(categoryResponseDTOs);
            dto.setProductSizeResponseDTOs(productSizeResponseDTOS);
            dto.setSupplier(productSupplierResponseDTO);
            dto.setPostResponseDTO(postResponseDTO);
            dto.setProductImageResponseDTOs(productImageResponseDTOS);
            return dto;
        });

    }
    @Override
    public ProductResponseDTO getProductById(UUID productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new EntityNotFoundException("Product không tồn tại !");
        }
        Product product = productOptional.get();
        List<CategoryResponseDTO> categoryResponseDTOs = getCategoryResponseDTOs(product);
        List<ProductImageResponseDTO> productImageResponseDTOS = getProductImageResponseDTOs(product);
        List<ProductSizeResponseDTO> productSizeResponseDTOS = getProductSizeResponseDTOs(product);
        ProductSupplierResponseDTO productSupplierResponseDTO = getProductSupplierResponseDTO(product);
        PostResponseDTO postResponseDTO = getPostResponseDTO(product);
        String productPriceSaleString = formatProductPriceSale(product);
        String productPriceString = formatProductPrice(product);

        ProductResponseDTO productDTO = new ProductResponseDTO();
        productDTO.toDto(product);
        productDTO.setProductPrice(productPriceString);
        productDTO.setProductPriceSale(productPriceSaleString);
        productDTO.setCategoryResponseDTO(categoryResponseDTOs);
        productDTO.setProductSizeResponseDTOs(productSizeResponseDTOS);
        productDTO.setSupplier(productSupplierResponseDTO);
        productDTO.setPostResponseDTO(postResponseDTO);
        productDTO.setProductImageResponseDTOs(productImageResponseDTOS);

        return productDTO;
    }



    public String formatPrice(double price) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
        format.setMinimumFractionDigits(PRODUCT_MIN_PRICE);
        format.setMaximumFractionDigits(PRODUCT_MIN_PRICE);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(price);
    }

    private boolean validatePriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice.compareTo(maxPrice) > PRODUCT_MIN_PRICE) {
            throw new NumberErrorException("Min price must be less than max price");
        }
        if (minPrice.compareTo(BigDecimal.ZERO) < PRODUCT_MIN_PRICE || maxPrice.compareTo(BigDecimal.ZERO) < PRODUCT_MIN_PRICE) {
            throw new NumberErrorException("Min price and max price must be greater than 0");
        }
        if (minPrice.compareTo(BigDecimal.ZERO) == PRODUCT_MIN_PRICE && maxPrice.compareTo(BigDecimal.ZERO) == PRODUCT_MIN_PRICE) {
            throw new NumberErrorException("Min price and max price must not be equal to 0");
        }
        if (maxPrice.compareTo(BigDecimal.valueOf(2000000)) > PRODUCT_MIN_PRICE) {
            throw new NumberErrorException("Max price must not be greater than 2 million");
        }
        return true;
    }


    private <E, D> List<D> convertToDTOList(List<E> entities, Function<E, D> converter) {
        return entities.stream().map(converter).collect(Collectors.toList());
    }

    public List<CategoryResponseDTO> getCategoryResponseDTOs(Product product) {
        List<CategoryResponseDTO> categoryResponseDTOs = convertToDTOList(product.getCategories() != null ? new ArrayList<>(product.getCategories()) : Collections.emptyList(), category -> {
            CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
            categoryResponseDTO.toDto(category);
            return categoryResponseDTO;
        });
        return categoryResponseDTOs;
    }

    public List<ProductImageResponseDTO> getProductImageResponseDTOs(Product product) {
        List<ProductImageResponseDTO> productImageResponseDTOS = convertToDTOList(product.getImages() != null ? new ArrayList<>(product.getImages()) : Collections.emptyList(), productImage -> {
            ProductImageResponseDTO productImageResponseDTO = new ProductImageResponseDTO();
            productImageResponseDTO.toDto(productImage);
            return productImageResponseDTO;
        });
        productImageResponseDTOS.sort(Comparator.comparing(ProductImageResponseDTO::getProductImageIndex));
        return productImageResponseDTOS;
    }

    public List<ProductSizeResponseDTO> getProductSizeResponseDTOs(Product product) {
        List<SizeProduct> sizeProducts = sizeProductRepository.findByProductId(product.getProductId());
        return product.getSizeProducts().stream().map(productSize -> {
            ProductSizeResponseDTO productSizeResponseDTO = new ProductSizeResponseDTO();
            productSizeResponseDTO.toDto(productSize.getSize());
            sizeProducts.stream().filter(sizeProduct -> sizeProduct.getSize().getProductSizeId().equals(productSize.getSize().getProductSizeId())).forEach(sizeProduct -> {
                SizeProductResponseDTO sizeProductDTO = new SizeProductResponseDTO();
                sizeProductDTO.toDto(sizeProduct);
                sizeProductDTO.setProductSizeQuantity(sizeProduct.getQuantity());
                productSizeResponseDTO.setSizeProductResponseDTOs(sizeProductDTO);
            });
            return productSizeResponseDTO;
        }).collect(Collectors.toList());
    }

    public ProductSupplierResponseDTO getProductSupplierResponseDTO(Product product) {
        ProductSupplierResponseDTO productSupplierResponseDTO = new ProductSupplierResponseDTO();
        if (product.getSupplier() != null) {
            productSupplierResponseDTO.toDto(product.getSupplier());
        }
        return productSupplierResponseDTO;
    }

    public PostResponseDTO getPostResponseDTO(Product product) {
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        if (product.getPost() != null) {
            postResponseDTO.toDto(product.getPost());
        }
        return postResponseDTO;
    }

    public String formatProductPriceSale(Product product) {
        double priceSale = product.getProductPriceSale();
        if (priceSale < PRODUCT_MIN_PRICE) {
            throw new NumberErrorException("Price must be greater than 0");
        }
        return formatPrice(priceSale);
    }

    public String formatProductPrice(Product product) {
        double price = product.getProductPrice();
        if (price < PRODUCT_MIN_PRICE) {
            throw new NumberErrorException("Price must be greater than 0");
        }
        return formatPrice(price);
    }

    public double solveProductSale(double productPrice, Coupon coupon) {
        double productSale = 0;
        if (coupon != null) {
            if (coupon.getCouponType() == COUPON_PER_HUNDRED_TYPE) {
                productSale = coupon.getCouponPerHundred();
            } else if (coupon.getCouponType() == COUPON_PRICE_TYPE) {
                double total = productPrice - coupon.getCouponPrice();
                if (total <= 0) {
                    return MAX_PER;
                }
                productSale = (SOLVE_SALE - (total / productPrice)) * MAX_PER;
                if (productSale > MAX_PER) {
                    return MAX_PER;
                }
            }
        }
        return productSale;
    }

}
