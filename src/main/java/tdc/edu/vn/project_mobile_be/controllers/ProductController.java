package tdc.edu.vn.project_mobile_be.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.MultipleFieldsNullOrEmptyException;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    private final SimpMessagingTemplate messagingTemplate;

    public ProductController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Operation(summary = "Get all products by Category", description = "Retrieve all products by category with pagination support")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content)
    })

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

    @PutMapping(value = {"/product", "/product/"})
    public ResponseEntity<ResponseData<?>> updateProduct(
            @Valid @RequestBody ProductUpdateRequestDTO params,
            BindingResult bindingResult,
            @RequestParam(value = "productId") UUID productId) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        Product product = productService.updateProduct(params, productId);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Cập nhật sản phẩm thành công", product);
        messagingTemplate.convertAndSend("/topic/products", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    @GetMapping(value = {"/test", "/tesst/"})
    public Long getProducts() {
        String time = productRepository.getLatestUpdatedTimestamp().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long timeInMilliSeconds = date.getTime();


        return timeInMilliSeconds;
    }

}
