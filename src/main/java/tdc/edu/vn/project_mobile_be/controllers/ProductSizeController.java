package tdc.edu.vn.project_mobile_be.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.MultipleFieldsNullOrEmptyException;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductSizeRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productsize.ProductSizeCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productsize.ProductSizeUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductSizeResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductSizeService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1")
public class ProductSizeController {

    @Autowired
    private ProductSizeService productSizeService;

    @GetMapping(value = {"/product-sizes/product", "/product-sizes/"})
    public ResponseEntity<ResponseData<?>> getAllProductSizesByProductId(@ModelAttribute ProductSizeRequestParamsDTO productSizeRequestParamsDTO) {

        List<ProductSizeResponseDTO> productSizes = productSizeService.getAllProductSizeByProductId(productSizeRequestParamsDTO);

        ResponseData<List<ProductSizeResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success", productSizes);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = {"/product-sizes/category/{categoryId}", "/product-sizes/category"})
    public ResponseEntity<ResponseData<?>> getAllProductSizesByCategoryID(@PathVariable(value = "categoryId",required = false) UUID categoryId) {

            List<ProductSizeResponseDTO> productSizes = productSizeService.getAllProductSizeByCategoryID(categoryId);

            ResponseData<List<ProductSizeResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success", productSizes);
            return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = {"/product-sizes", "/product-sizes/"})
    public ResponseEntity<ResponseData<PagedModel<EntityModel<ProductSizeResponseDTO>>>> getAllProductSizes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            PagedResourcesAssembler<ProductSizeResponseDTO> assembler
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductSizeResponseDTO> productSizeDtoPage = productSizeService.getAllProductSize(pageable);
        if (productSizeDtoPage.isEmpty()) {
            ResponseData<PagedModel<EntityModel<ProductSizeResponseDTO>>> responseData = new ResponseData<>(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm size", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        PagedModel<EntityModel<ProductSizeResponseDTO>> pagedModel = assembler.toModel(productSizeDtoPage);

        ResponseData<PagedModel<EntityModel<ProductSizeResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping(value = {"/product-sizes/{productSizeId}", "/product-sizes/{productSizeId}/"})
    public ResponseEntity<ResponseData<?>> deleteProductSize(@PathVariable UUID productSizeId) {
        boolean isDeleted = productSizeService.deleteProductSize(productSizeId);
        if (!isDeleted) {
            ResponseData<?> responseData = new ResponseData<>(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm size", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Xóa thành công", null);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping(value = {"/product-sizes/{productSizeId}", "/product-sizes/{productSizeId}/"})
    public ResponseEntity<ResponseData<?>> updateProductSize(
            @PathVariable UUID productSizeId,
            @Valid @RequestBody ProductSizeUpdateRequestDTO request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        ProductSize productSize = productSizeService.updateProductSize(request, productSizeId);
        ProductSizeResponseDTO productSizeResponseDTO = new ProductSizeResponseDTO();
        productSizeResponseDTO.toDto(productSize);
        ResponseData<ProductSizeResponseDTO> responseData = new ResponseData<>(HttpStatus.OK, "Cập nhật thành công", productSizeResponseDTO);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping(value = {"/product-sizes", "/product-sizes/"})
    public ResponseEntity<ResponseData<?>> createProductSize(
            @Valid @RequestBody ProductSizeCreateRequestDTO request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        ProductSize productSize = productSizeService.createProductSize(request);
        ProductSizeResponseDTO productSizeResponseDTO = new ProductSizeResponseDTO();
        productSizeResponseDTO.toDto(productSize);
        ResponseData<ProductSizeResponseDTO> responseData = new ResponseData<>(HttpStatus.CREATED, "Tạo mới thành công", productSizeResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }
}
