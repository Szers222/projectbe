package tdc.edu.vn.project_mobile_be.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.commond.Breadcrumb;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.MultipleFieldsNullOrEmptyException;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductListeners;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.BreadcrumbService;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "Product Controller", description = "Endpoints for managing products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private BreadcrumbService breadcrumbService;
    @Autowired
    private ProductRepository p;
    @Autowired
    private SimpMessagingTemplate template;

    private final ObjectMapper objectMapper;

    @Autowired
    public ProductController(@Lazy ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Operation(summary = "Get all products by Category", description = "Retrieve all products by category with pagination support")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content)
    })

    @GetMapping(value = {"/products/filters", "/products"})
    public ResponseEntity<ResponseData<PagedModel<EntityModel<ProductResponseDTO>>>> getProductsByFilters(
            @ModelAttribute ProductRequestParamsDTO params,
            PagedResourcesAssembler<ProductResponseDTO> assembler) {

        if (params.getSort() == null || params.getSort().isEmpty()) {
            params.setSort("productPrice");
        }
        Sort.Direction sortDirection = params.getDirection().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(sortDirection, params.getSort());
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sortBy);

        // Sử dụng Specifications để kết hợp nhiều kiểu filter
        Page<ProductResponseDTO> productDtoPage = productService.findProductsByFilters(params, pageable);

        // Chuyển đổi sang PagedModel
        PagedModel<EntityModel<ProductResponseDTO>> pagedModel = assembler.toModel(productDtoPage);

        // Đóng gói response
        ResponseData<PagedModel<EntityModel<ProductResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);

        return ResponseEntity.ok(responseData);
    }

    @PostMapping(value = {"/product", "/product/"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<?>> createProduct(
            @RequestPart("file") MultipartFile[] files,
            @Valid @RequestPart("params") String paramsJson,
            BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }


        ProductCreateRequestDTO params = objectMapper.readValue(paramsJson, ProductCreateRequestDTO.class);

        Product product = productService.createProduct(params, files);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Tạo Sản Phẩm Thành Công", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    @PutMapping(value = {"/product/{productId}", "/product/{productId}/"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<?>> updateProduct(
            @Valid @RequestPart String paramsJson,
            @PathVariable("productId") UUID productId,
            @RequestPart("file") MultipartFile[] files,
            BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        ProductUpdateRequestDTO params = objectMapper.readValue(paramsJson, ProductUpdateRequestDTO.class);

        Product product = productService.updateProduct(params, productId, files);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Cập nhật sản phẩm thành công", product);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = {"/products/relate/{categoryId}", "/product/relate/{categoryId}/"})
    public ResponseEntity<ResponseData<List<ProductResponseDTO>>> getProductsByCategory(
            @PathVariable("categoryId") UUID categoryId) {

        List<ProductResponseDTO> productData = productService.findProductRelate(categoryId);
        ResponseData<List<ProductResponseDTO>> responseData;
        if (productData.isEmpty()) {
            responseData = new ResponseData<>(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        responseData = new ResponseData<>(HttpStatus.OK, "Success", productData);
        return ResponseEntity.ok(responseData);
    }


    @GetMapping("/products/{categoryId}/{productId}")
    public ResponseEntity<List<Breadcrumb>> productDetail(@PathVariable UUID categoryId, @PathVariable UUID productId) {
        List<Breadcrumb> breadcrumbs = breadcrumbService.generateProductBreadcrumb(categoryId, productId);
        return new ResponseEntity<>(breadcrumbs, HttpStatus.OK);
    }

    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<ResponseData<List<ProductResponseDTO>>> getProductByCategoryId(
            @PathVariable UUID categoryId,
            @ModelAttribute ProductRequestParamsDTO params,
            PagedResourcesAssembler<ProductResponseDTO> assembler) {
        if (params.getSort() == null || params.getSort().isEmpty()) {
            params.setSort("productPriceSale");
        }
        Sort.Direction sortDirection = params.getDirection().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(sortDirection, params.getSort());
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sortBy);

        List<ProductResponseDTO> dtoList = productService.getProductByCategoryId(categoryId);


        ResponseData<List<ProductResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success", dtoList);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseData<ProductResponseDTO>> getProductById(@PathVariable("productId") UUID productId) {
        ProductResponseDTO product = productService.getProductById(productId);
        ResponseData<ProductResponseDTO> responseData = new ResponseData<>(HttpStatus.OK, "Success", product);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/product/size")
    public ResponseEntity<ResponseData<Product>> getProductByIdSize(@RequestParam UUID sizerID, @RequestParam UUID productId) {
        Product product = p.findBySizeId(productId, sizerID);
        ResponseData<Product> responseData = new ResponseData<>(HttpStatus.OK, "Success", product);
        return ResponseEntity.ok(responseData);
    }


    @DeleteMapping("/product/{productId}")
    @Transactional
    public ResponseEntity<ResponseData<?>> deleteProduct(@PathVariable("productId") UUID productId) {
        productService.deleteProduct(productId);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Xóa sản phẩm thành công", null);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @EventListener(ProductListeners.class)
    @SendTo("/topic/products")
    public void handleProductUpdated(ProductListeners event) {
        ProductResponseDTO product = event.getProduct();
        this.template.convertAndSend("/topic/products", product);
    }

    @GetMapping("/products/test/{categoryId}")
    public ResponseEntity<ResponseData<List<Product>>> getAllProductsByCategories(
            @PathVariable UUID categoryId

    ) {
        List<Product> products = p.findByIdWithCategories(categoryId);
        ResponseData<List<Product>> responseData = new ResponseData<>(HttpStatus.OK, "Success", products);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/products/new/created")
    public ResponseEntity<ResponseData<List<ProductResponseDTO>>> getTop10NewProducts() {
        List<ProductResponseDTO> products = productService.getProductNew();
        ResponseData<List<ProductResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success", products);
        return ResponseEntity.ok(responseData);
    }
}
