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
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.status.PostStatus;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.*;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
        if (params.getCategoryId() != null && !categoryRepository.findById(params.getCategoryId()).isEmpty()) {
            spec = spec.and(ProductSpecifications.hasCategory(params.getCategoryId()));
        }

        // Lọc theo khoảng giá
        if (params.getMinPrice() != null && params.getMaxPrice() != null) {
            if (params.getMinPrice().compareTo(params.getMaxPrice()) > 0) {
                throw new NumberErrorException("Min price must be less than max price");
            }
            if (params.getMinPrice().compareTo(BigDecimal.ZERO) < 0 || params.getMaxPrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new NumberErrorException("Min price and max price must be greater than 0");
            }
            if (params.getMinPrice().compareTo(BigDecimal.ZERO) == 0 && params.getMaxPrice().compareTo(BigDecimal.ZERO) == 0) {
                throw new NumberErrorException("Min price and max price must not be equal to 0");
            }
            if (params.getMaxPrice().compareTo(BigDecimal.valueOf(2000000)) > 0) {
                throw new NumberErrorException("Max price must not be greater than 2 million");
            }
            spec = spec.and(ProductSpecifications.priceBetween(params.getMinPrice(), params.getMaxPrice()));
        }

        // Lọc theo kích thước
        if (params.getSizeIds() != null && !params.getSizeIds().isEmpty()) {
            spec = spec.and(ProductSpecifications.hasSizes(params.getSizeIds()));
        }

        // Lọc theo nhà cung cấp (supplier)
        if (params.getSupplierId() != null && !productSupplierRepository.findById(params.getSupplierId()).isEmpty()) {
            spec = spec.and(ProductSpecifications.hasSupplier(params.getSupplierId()));
        }
        if (params.getDirection() != null && params.getSort() != null) {
            spec = spec.and(ProductSpecifications.hasSort(params.getSort(), params.getDirection()));
        }
        if (params.getSearch() == null || params.getSearch().isEmpty() || params.getSearch().isBlank()) {

        }
        if (params.getSearch() != null) {
            spec = spec.and(ProductSpecifications.hasSearch(params.getSearch()));
        }

        // Truy vấn với các tiêu chí kết hợp
        Page<Product> products = productRepository.findAll(spec, pageable);
        if (products.isEmpty() || products.getSize() == 0) {
            throw new ListNotFoundException("Không tìm thấy sản phẩm");
        }

        return products.map(product -> {
            Product productWithImages = productRepository.findByIdWithImages(product.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(productWithImages);  // Mapping product with images to the DTO
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

}
