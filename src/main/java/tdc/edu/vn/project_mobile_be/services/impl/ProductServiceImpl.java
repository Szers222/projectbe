package tdc.edu.vn.project_mobile_be.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
import tdc.edu.vn.project_mobile_be.dtos.requests.sizeproduct.SizeProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.category.CategoryResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.coupon.CouponResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.post.PostResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.*;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.entities.product.*;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProductId;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.*;
import tdc.edu.vn.project_mobile_be.interfaces.service.CouponService;
import tdc.edu.vn.project_mobile_be.interfaces.service.PostService;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductImageService;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl extends AbService<Product, UUID> implements ProductService {

    private final int COUPON_PER_HUNDRED_TYPE = 0;
    private final int COUPON_PRICE_TYPE = 1;
    private final int PRODUCT_DEFAULT_SIZE = 0;
    private final int PRODUCT_RELATE_SIZE = 6;
    private final int PRODUCT_MIN_PRICE = 0;
    private final int PRODUCT_MIN_SIZE = 0;
    private final double SOLVE_SALE = 1;
    private final double MAX_PER = 100;

    private final ObjectMapper objectMapper;
    @Autowired
    private ShipmentProductRepository shipmentProductRepository;

    @Autowired
    public ProductServiceImpl(@Lazy ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private ProductSupplierRepository productSupplierRepository;
    @Autowired
    private SizeProductRepository sizeProductRepository;
    @Autowired
    private CouponService couponService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    @Transactional
    public Product createProduct(ProductCreateRequestDTO params, MultipartFile[] files) {
        Post post;
        if (params.getPost() != null) {
            post = postService.createPost(params.getPost());
            if (post == null) {
                throw new EntityNotFoundException("Post tao moi khong thanh cong");
            }
        } else {
            post = null;
        }
        Coupon coupon;
        if (params.getCoupon() == null) {
            coupon = null;
        } else {
            coupon = couponService.createCoupon(params.getCoupon());
            if (coupon == null) {
                throw new EntityNotFoundException("Coupon tao moi khong thanh cong");
            }
        }

        Set<Category> categories = retrieveCategories(params.getCategoryId());
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Category not found !");
        }
        ProductSupplier productSupplier = productSupplierRepository.findProductSupplierById(params.getProductSupplier());
        if (productSupplier == null) {
            throw new EntityNotFoundException("ProductSupplier not found !");
        }
        double productSale = solveProductSale(params.getProductPrice(), coupon);
        // Tạo đối tượng Product mới
        Product product = params.toEntity();
        product.setProductId(UUID.randomUUID());
        product.setCategories(categories);
        product.setPost(post);
        product.setCoupon(coupon);
        product.setProductSale(productSale);
        product.setSupplier(productSupplier);
        Product savedProduct = productRepository.save(product);
        if (files.length != 0) {
            if(params.getProductImageResponseDTOs() == null){
                throw new EntityNotFoundException("Params khong co request image");
            }
            Set<ProductImage> productImages = productImageService.createProductImageWithProduct(
                    params.getProductImageResponseDTOs(),
                    savedProduct.getProductId(),
                    files);
            savedProduct.setImages(productImages);
        }
        Set<SizeProduct> sizeProducts;
        if (params.getSizesProduct() != null) {
            sizeProducts = createSizeProducts(params.getSizesProduct(), savedProduct);
            savedProduct.getSizeProducts().clear();
            savedProduct.setProductQuantity(PRODUCT_DEFAULT_SIZE);
            savedProduct.getSizeProducts().addAll(sizeProducts);
        }
        ProductResponseDTO dto = getProductById(savedProduct.getProductId());
        applicationEventPublisher.publishEvent(new ProductListeners(this, dto));
        return savedProduct;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public Product updateProduct(ProductUpdateRequestDTO params, UUID productId, MultipartFile[] files) {
        Post post = new Post();
        if (params.getPost() != null) {
            post = postService.updatePostByProductId(params.getPost(), productId);
            if (post == null) {
                throw new EntityNotFoundException("Post không tồn tại !");
            }
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("Product không tồn tại !");
        }
        Product product = optionalProduct.get();
        Set<Category> categories = retrieveCategories(params.getCategoryId());
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Category không tồn tại !");
        }
        Set<SizeProduct> sizeProducts = updateSizeProducts(params.getSizesProduct(), product);
        if (sizeProducts.isEmpty()) {
            throw new EntityNotFoundException("SizeProduct không tồn tại !");
        }
        Set<ProductImage> productImages = productImageService.updateProductImageForProduct(
                params.getProductImageResponseDTOs(), productId,
                files);
        if (productImages.isEmpty()) {
            throw new EntityNotFoundException("ProductImage không tồn tại !");
        }
        ProductSupplier productSupplier = productSupplierRepository.findProductSupplierById(params.getProductSupplier());
        if (productSupplier == null) {
            throw new EntityNotFoundException("ProductSupplier not found !");
        }

        Coupon coupon = new Coupon();
        if (params.getCoupon() != null) {

            if (product.getCoupon() != null) {

                coupon = couponService.updateCouponByProductId(params.getCoupon(), productId);
            } else {

                coupon = couponService.createCouponForProduct(params.getCoupon());
            }
            product.setCoupon(coupon);

        } else if (product.getCoupon() != null) {

            couponService.deleteCoupon(product.getCoupon().getCouponId());

            product.setCoupon(null);
        }

        double productSale = solveProductSale(params.getProductPrice(), coupon);


        int quantity = setQuantityProduct(sizeProducts);
        product.setProductQuantity(quantity);
        product.setProductName(params.getProductName());
        product.setProductQuantity(params.getProductQuantity());
        product.setProductYearOfManufacture(params.getProductYearOfManufacture());
        product.setPost(post);
        product.setSupplier(productSupplier);
        product.setCategories(categories);
        product.setProductSale(productSale);
        product.getImages().addAll(productImages);
        product.getSizeProducts().addAll(sizeProducts);
        ProductResponseDTO dto = getProductById(productId);
        applicationEventPublisher.publishEvent(new ProductListeners(this, dto));

//        redisTemplate.delete(productId.toString());
//
//        redisTemplate.opsForValue().set(productId.toString(), product, 60, TimeUnit.MINUTES);

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public List<ProductResponseDTO> findProductRelate(UUID categoryId) {
        List<Product> productRelate = productRepository.findByIdWithCategories(categoryId);

        List<Product> result = new ArrayList<>(productRelate);
        if (productRelate.size() < PRODUCT_RELATE_SIZE) {
            List<Product> products = new ArrayList<>(productRepository.findAll());
            if (products.isEmpty() || products.size() < PRODUCT_RELATE_SIZE - productRelate.size()) {
                throw new EntityNotFoundException("So luong  san pham khong du");
            }
            while (productRelate.size() < PRODUCT_RELATE_SIZE) {
                int addRandomProduct = new Random().nextInt(products.size());
                Product product = products.get(addRandomProduct);
                if (!productRelate.contains(product)) {
                    productRelate.add(product);
                }
            }
        }
        while (result.size() < PRODUCT_RELATE_SIZE) {
            int addRandomProduct = new Random().nextInt(productRelate.size());
            Product product = productRelate.get(addRandomProduct);
            if (!result.contains(product)) {
                result.add(product);
            }
        }


        List<ProductResponseDTO> finalResult = new ArrayList<>();
        result.forEach(product -> {
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
            finalResult.add(dto);
        });
        return finalResult;
    }

    @Override
    @Transactional
    public Page<ProductResponseDTO> findProductsByFilters(ProductRequestParamsDTO params, Pageable pageable) {

        // Serialize filter parameters
        String filterJson = "";
        try {
            filterJson = objectMapper.writeValueAsString(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Serialize only relevant parts of Pageable
        String pageableKey = "page:" + pageable.getPageNumber() +
                ",size:" + pageable.getPageSize() +
                ",sort:" + (pageable.getSort() != null ? pageable.getSort().toString() : "");

        String cacheKey = "findProductsByFilters:" + filterJson + ":" + pageableKey;
        log.info("Cache key: {}", cacheKey);
        // Try to get from cache
        String cachedResult = (String) redisTemplate.opsForValue().get(cacheKey);
        log.info("Cache key123: {}", cachedResult);
        if (cachedResult != null) {
            try {
                List<ProductResponseDTO> dtoList = objectMapper.readValue(cachedResult, new TypeReference<>() {
                });
                // No need to check for null, objectMapper.readValue throws exception if it's null or invalid
                return new PageImpl<>(dtoList, pageable, dtoList.size());
            } catch (IOException e) {
                // Log the exception and continue to fetch from DB
                e.printStackTrace();
            }
        }

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
        Page<ProductResponseDTO> dtoPage = products.map(product -> {
            List<CategoryResponseDTO> categoryResponseDTOs = getCategoryResponseDTOs(product);
            List<ProductImageResponseDTO> productImageResponseDTOS = getProductImageResponseDTOs(product);
            List<ProductSizeResponseDTO> productSizeResponseDTOS = getProductSizeResponseDTOs(product);
            ProductSupplierResponseDTO productSupplierResponseDTO = getProductSupplierResponseDTO(product);
            PostResponseDTO postResponseDTO = getPostResponseDTO(product);
            String productPriceSaleString = formatProductPriceSale(product);
            String productPriceString = formatProductPrice(product);
            CouponResponseDTO couponResponseDTO = new CouponResponseDTO();
            if (product.getCoupon() != null) {
                couponResponseDTO.toDto(product.getCoupon());
            }

            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(product);
            dto.setProductPrice(productPriceString);
            dto.setCategoryResponseDTO(categoryResponseDTOs);
            dto.setProductSizeResponseDTOs(productSizeResponseDTOS);
            dto.setSupplier(productSupplierResponseDTO);
            dto.setPostResponseDTO(postResponseDTO);
            dto.setProductImageResponseDTOs(productImageResponseDTOS);
            dto.setProductPriceSale(productPriceSaleString);
            dto.setCouponResponseDTO(couponResponseDTO);
            return dto;
        });

        List<ProductResponseDTO> dtoList = dtoPage.getContent();
        try {
            String serializedData = objectMapper.writeValueAsString(dtoList);
            redisTemplate.opsForValue().set(cacheKey, serializedData, 60, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtoPage;

    }

    @Override
    @Transactional
    public void deleteProduct(UUID productId) {


        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));


        // Xóa các hình ảnh của sản phẩm
        for (ProductImage productImage : product.getImages()) {
            if (productImage != null) {
                googleCloudStorageService.deleteFile(productImage.getProductImagePath());
            }
        }

        // Xóa các liên kết với SizeProduct
        product.getSizeProducts().forEach(sizeProduct -> sizeProduct.setProduct(null));
        product.getSizeProducts().clear();

        // Xóa các liên kết với ShipmentProduct trước khi xóa product
        Set<ShipmentProduct> shipmentProducts = product.getShipmentProducts();
        shipmentProducts.clear(); // Xóa toàn bộ liên kết ShipmentProduct trước

        productRepository.saveAndFlush(product); // Sau khi đã quản lý tất cả các liên kết

        shipmentProductRepository.deleteAll(shipmentProducts); // Sau đó xóa ShipmentProduct nếu cần

        // Xóa sản phẩm và gửi thông điệp
        productRepository.delete(product);
        messagingTemplate.convertAndSend("/topic/products", productId);
    }


    @Override
    public List<ProductResponseDTO> getProductByCategoryId(UUID categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        Category category = categoryRepository.findByCategoryId(categoryId);
        if (category == null) {
            throw new EntityNotFoundException("Category không tồn tại !");
        }
        if (products.isEmpty() && category.getChildren().isEmpty()) {
            throw new ListNotFoundException("Không tìm thấy sản phẩm");
        }

        if (category.getChildren() != null) {
            List<Category> categoryChildren = category.getChildren();
            categoryChildren.forEach(categoryChild -> {
                List<Product> productsChild = productRepository.findByCategoryId(categoryChild.getCategoryId());
                products.addAll(productsChild);
            });
        }
        List<ProductResponseDTO> result = new ArrayList<>();
        products.forEach(product -> {
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

            result.add(dto);
        });
        return result;
    }
    @Override
    public ProductResponseDTO getProductById(UUID productId) {
        String cacheKey = "product:" + productId;
//        String cachedProduct = (String) redisTemplate.opsForValue().get(cacheKey);
//        if (cachedProduct != null) {
//            try {
//                return objectMapper.readValue(cachedProduct, ProductResponseDTO.class);
//            } catch (IOException e) {
//                log.error("Error reading product from cache: {}", e.getMessage());
//            }
//        }
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
        CouponResponseDTO couponResponseDTO = new CouponResponseDTO();
        if (product.getCoupon() != null) {
            couponResponseDTO.toDto(product.getCoupon());
        }

        ProductResponseDTO productDTO = new ProductResponseDTO();
        productDTO.toDto(product);
        productDTO.setProductPrice(productPriceString);
        productDTO.setProductPriceSale(productPriceSaleString);
        productDTO.setCategoryResponseDTO(categoryResponseDTOs);
        productDTO.setProductSizeResponseDTOs(productSizeResponseDTOS);
        productDTO.setSupplier(productSupplierResponseDTO);
        productDTO.setPostResponseDTO(postResponseDTO);
        productDTO.setProductImageResponseDTOs(productImageResponseDTOS);
        productDTO.setCouponResponseDTO(couponResponseDTO);
//        try {
//            String serializedProduct = objectMapper.writeValueAsString(productDTO);
//            redisTemplate.opsForValue().set(cacheKey, serializedProduct, 60, TimeUnit.MINUTES);
//        } catch (IOException e) {
//            log.error("Error writing product to cache: {}", e.getMessage());
//        }
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
        return convertToDTOList(product.getCategories() != null ? new ArrayList<>(product.getCategories()) : Collections.emptyList(), category -> {
            CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
            categoryResponseDTO.toDto(category);
            return categoryResponseDTO;
        });
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

    private Set<Category> retrieveCategories(List<UUID> categoryIds) {
        if (categoryIds.isEmpty()) {
            throw new EntityNotFoundException("Category not found !");
        }
        Set<Category> categories = new HashSet<>();
        for (UUID categoryId : categoryIds) {
            Category category = categoryRepository.findByCategoryId(categoryId);
            if (category == null) {
                throw new EntityNotFoundException("Category not found for ID: " + categoryId);
            }
            categories.add(category);
        }
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Category not found !");
        }
        return categories;
    }

    private Set<SizeProduct> updateSizeProducts(List<SizeProductRequestParamsDTO> paramsDTOS, Product product) {
        Set<SizeProduct> sizeProducts = new HashSet<>();
        for (SizeProductRequestParamsDTO param : paramsDTOS) {
            ProductSize productSize = productSizeRepository.findByProductSizeId(param.getSizeId());
            if (productSize == null) {
                throw new EntityNotFoundException("Size not found for ID: " + param.getSizeId());
            }
            SizeProductId sizeProductId = new SizeProductId(product.getProductId(), param.getSizeId());
            SizeProduct sizeProduct = sizeProductRepository.findById(sizeProductId).orElse(null);

            if (sizeProduct == null) {
                sizeProduct = new SizeProduct();
                sizeProduct.setId(sizeProductId);
            }
            sizeProduct.setProduct(product);
            sizeProduct.setSize(productSize);

            sizeProductRepository.save(sizeProduct);
            sizeProducts.add(sizeProduct);
        }
        if (sizeProducts.isEmpty()) {
            throw new EntityNotFoundException("SizeProducts not found !");
        }
        return sizeProducts;
    }
    private Set<SizeProduct> createSizeProducts(List<SizeProductRequestParamsDTO> paramsDTOS, Product product) {
        Set<SizeProduct> sizeProducts = new HashSet<>();
        for (SizeProductRequestParamsDTO param : paramsDTOS) {
            ProductSize productSize = productSizeRepository.findByProductSizeId(param.getSizeId());
            if (productSize == null) {
                throw new EntityNotFoundException("Size not found for ID: " + param.getSizeId());
            }

            SizeProduct sizeProduct = sizeProductRepository.findBySizeIdAndProductId(product.getProductId(), param.getSizeId());
            log.info("SizeProduct: {}", sizeProduct);
            if (sizeProduct == null) {
                sizeProduct = new SizeProduct();
            }
            sizeProduct.setProduct(product);
            sizeProduct.setSize(productSize);
            sizeProducts.add(sizeProduct);
        }
        if (sizeProducts.isEmpty()) {
            throw new EntityNotFoundException("SizeProduct not found !");
        }
        return sizeProducts;
    }

    public int setQuantityProduct(Set<SizeProduct> sizeProducts) {
        int quantity = 0;
        for (SizeProduct sizeProduct : sizeProducts) {
            quantity += sizeProduct.getQuantity();
        }
        return quantity;
    }


}

