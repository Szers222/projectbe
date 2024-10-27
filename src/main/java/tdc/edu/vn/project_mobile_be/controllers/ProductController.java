package tdc.edu.vn.project_mobile_be.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.Breadcrumb;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.MultipleFieldsNullOrEmptyException;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.BreadcrumbService;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductService;


import java.util.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "Product Controller", description = "Endpoints for managing products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BreadcrumbService breadcrumbService;



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

    @PostMapping(value = {"/product", "/product/"})
    public ResponseEntity<ResponseData<?>> createProduct(
            @Valid @RequestBody ProductCreateRequestDTO params,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        Product product = productService.createProduct(params);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Tạo Sản Phẩm Thành Công", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }


    @GetMapping(value = {"/products/relate/{categoryId}", "/product/relate/{categoryId}/"})
    public ResponseEntity<ResponseData<PagedModel<EntityModel<ProductResponseDTO>>>> getProductsByCategory(
            @PathVariable("categoryId") UUID categoryId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            PagedResourcesAssembler<ProductResponseDTO> assembler) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("productSale").descending());
        Page<ProductResponseDTO> productDtoPage = productService.findProductRelate(categoryId, pageable);
        if (productDtoPage.isEmpty()) {
            ResponseData<PagedModel<EntityModel<ProductResponseDTO>>> responseData = new ResponseData<>(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        List<ProductResponseDTO> list = productDtoPage.getContent();
        if (list.isEmpty()) {
            ResponseData<PagedModel<EntityModel<ProductResponseDTO>>> responseData = new ResponseData<>(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        List<ProductResponseDTO> listRnd = new ArrayList<>(list);

        Collections.shuffle(listRnd, new Random());
        listRnd = listRnd.subList(0, Math.min(listRnd.size(), 6));

        PagedModel<EntityModel<ProductResponseDTO>> pagedModel = assembler.toModel(new PageImpl<>(listRnd, pageable, listRnd.size()));
        ResponseData<PagedModel<EntityModel<ProductResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);
        return ResponseEntity.ok(responseData);
    }


    @GetMapping("/products/{categoryId}/{productId}")
    public ResponseEntity<List<Breadcrumb>> productDetail(@PathVariable UUID categoryId, @PathVariable UUID productId) {
        List<Breadcrumb> breadcrumbs = breadcrumbService.generateProductBreadcrumb(categoryId, productId);
        return new ResponseEntity<>(breadcrumbs, HttpStatus.OK);
    }

    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<ResponseData<PagedModel<EntityModel<ProductResponseDTO>>>> getProductByCategoryId(
            @PathVariable UUID categoryId,
            @ModelAttribute ProductRequestParamsDTO params,
            PagedResourcesAssembler<ProductResponseDTO> assembler) {
        if (params.getSort() == null || params.getSort().isEmpty()) {
            params.setSort("productPrice");
        }
        Sort.Direction sortDirection = params.getDirection().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(sortDirection, params.getSort());
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sortBy);

        Page<ProductResponseDTO> productDtoPage = productService.getProductByCategoryId(categoryId, pageable);

        PagedModel<EntityModel<ProductResponseDTO>> pagedModel = assembler.toModel(productDtoPage);

        ResponseData<PagedModel<EntityModel<ProductResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseData<ProductResponseDTO>> getProductById(@PathVariable UUID productId) {
        ProductResponseDTO product = productService.getProductById(productId);
        ResponseData<ProductResponseDTO> responseData = new ResponseData<>(HttpStatus.OK, "Success", product);
        return ResponseEntity.ok(responseData);
    }

}
