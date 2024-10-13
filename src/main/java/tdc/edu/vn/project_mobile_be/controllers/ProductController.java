package tdc.edu.vn.project_mobile_be.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductSizeResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductSupplierResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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


    @Operation(summary = "Get all products", description = "Retrieve all products with pagination support")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content)
    })


    @GetMapping(value = {"/products", "/products/"})
    public ResponseEntity<ResponseData<PagedModel<EntityModel<ProductResponseDTO>>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "productId") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            PagedResourcesAssembler<ProductResponseDTO> assembler) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(sortDirection, sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<ProductResponseDTO> productDtoPage = productService.findAll(pageable); // Lấy Page<ProductResponseDTO>
        PagedModel<EntityModel<ProductResponseDTO>> pagedModel = assembler.toModel(productDtoPage); // Chuyển đổi sang PagedModel

        ResponseData<PagedModel<EntityModel<ProductResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "Get all products by Category", description = "Retrieve all products by category with pagination support")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content)
    })
    // xuất danh mục
    @GetMapping(value = {"/products/category", "/products/category/"})
    public ResponseEntity<ResponseData<PagedModel<EntityModel<ProductResponseDTO>>>> getAllProductsByCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam UUID categoryId,
            PagedResourcesAssembler<ProductResponseDTO> assembler) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(sortDirection, sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<ProductResponseDTO> productDtoPage = productService.findAllByCategory(categoryId, pageable);
        PagedModel<EntityModel<ProductResponseDTO>> pagedModel = assembler.toModel(productDtoPage); // Chuyển đổi sang PagedModel

        ResponseData<PagedModel<EntityModel<ProductResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = {"/products/price-range", "/products/price-range/"})
    public ResponseEntity<ResponseData<PagedModel<EntityModel<ProductResponseDTO>>>> getAllProductsByPriceRange(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            PagedResourcesAssembler<ProductResponseDTO> assembler) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(sortDirection, sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<ProductResponseDTO> productDtoPage = productService.findProductsPriceRange(minPrice, maxPrice, pageable);
        PagedModel<EntityModel<ProductResponseDTO>> pagedModel = assembler.toModel(productDtoPage); // Chuyển đổi sang PagedModel

        ResponseData<PagedModel<EntityModel<ProductResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = {"/products/sizes", "/products/sizes/"})
    public ResponseEntity<ResponseData<PagedModel<EntityModel<ProductResponseDTO>>>> getAllProductsBySizes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam List<UUID> sizeId,
            PagedResourcesAssembler<ProductResponseDTO> assembler) {


        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(sortDirection, sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<ProductResponseDTO> productDtoPage = productService.findBySizes(sizeId, pageable);
        PagedModel<EntityModel<ProductResponseDTO>> pagedModel = assembler.toModel(productDtoPage); // Chuyển đổi sang PagedModel

        ResponseData<PagedModel<EntityModel<ProductResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = {"/products/suplier", "/products/suplier/"})
    public ResponseEntity<ResponseData<PagedModel<EntityModel<ProductResponseDTO>>>> getAllProductsBySuplier(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam UUID suplierId,
            PagedResourcesAssembler<ProductResponseDTO> assembler) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(sortDirection, sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<ProductResponseDTO> productDtoPage = productService.findByIdSuplier(suplierId, pageable);
        PagedModel<EntityModel<ProductResponseDTO>> pagedModel = assembler.toModel(productDtoPage); // Chuyển đổi sang PagedModel

        ResponseData<PagedModel<EntityModel<ProductResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = {"/products/test", "/product/"})
    public List<ProductSupplierResponseDTO> createProduct(@RequestParam UUID categoryId) {
        return productRepository.findProductSuplierByCategory(categoryId).stream().map(productSupplier -> {
            ProductSupplierResponseDTO dto = new ProductSupplierResponseDTO();
            dto.toDto(productSupplier);
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping(value = {"/products/test1", "/product/"})
    public List<ProductSizeResponseDTO> createProduct1(@RequestParam UUID categoryId) {
        return productRepository.findProductSizesByCategory(categoryId).stream().map(productSupplier -> {
            ProductSizeResponseDTO dto = new ProductSizeResponseDTO();
            dto.toDto(productSupplier);
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping(value = {"/products/sizes-supplier", "/product/sizes-supplier/"})
    public ResponseEntity<ResponseData<Map<String, Object>>> getProductSizesAndSuppliers
            (@RequestParam UUID categoryId) {
        ProductRequestParamsDTO params = new ProductRequestParamsDTO();
        params.setCategoryId(categoryId);

        List<ProductSize> productSizes = productService.findProductSizesByCategory(params);
        List<ProductSupplier> productSuppliers = productService.findProductSuppliersByCategory(params);
        System.console().printf("category id: %s", productSuppliers);
        System.console().printf("category id: %s", productSizes);

        Map<String, Object> data = new HashMap<>();
        data.put("sizes", productSizes);
        data.put("suppliers", productSuppliers);
        ResponseData<Map<String, Object>> responseData = new ResponseData<>(HttpStatus.OK, "Success", data);

        return ResponseEntity.ok(responseData);

    }
    @GetMapping(value = {"/products/filters", "/products/filters/"})
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
}
