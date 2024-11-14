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
import tdc.edu.vn.project_mobile_be.dtos.requests.productsupplier.ProductSupplierCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productsupplier.ProductSupplierUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductSupplierResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductSupplierService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1")
public class ProductSupplierController {

    @Autowired
    private ProductSupplierService supplierService;

    // Endpoint to get all product suppliers by category ID
    @GetMapping(value = {"/product-suppliers/category/{categoryId}", "/product-suppliers/category"})
    public ResponseEntity<ResponseData<?>> getAllProductSizesByCategoryID(@PathVariable(value = "categoryId", required = false) UUID categoryId) {
        List<ProductSupplierResponseDTO> supplierResponseDTOS = supplierService.getAllProductSupplier(categoryId);
        ResponseData<List<ProductSupplierResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success", supplierResponseDTOS);
        return ResponseEntity.ok(responseData);
    }

    // Endpoint to create a new product supplier
    @PostMapping(value = {"/product-supplier", "/product-supplier/"})
    public ResponseEntity<ResponseData<?>> createProductSupplier(
            @Valid @RequestBody ProductSupplierCreateRequestDTO request,
            BindingResult bindingResult) {
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        // Create the product supplier
        ProductSupplier productSupplier = supplierService.createProductSupplier(request);
        ProductSupplierResponseDTO productSupplierResponseDTO = new ProductSupplierResponseDTO();
        productSupplierResponseDTO.toDto(productSupplier);
        ResponseData<ProductSupplierResponseDTO> responseData = new ResponseData<>(HttpStatus.CREATED, "Tạo mới thành công", productSupplierResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    // Endpoint to update an existing product supplier
    @PutMapping(value = {"/product-supplier/{productSupplierId}", "/product-supplier/{productSupplierId}/"})
    public ResponseEntity<ResponseData<?>> updateProductSupplier(
            @PathVariable(value = "productSupplierId") UUID productSupplierId,
            @Valid @RequestBody ProductSupplierUpdateRequestDTO request,
            BindingResult bindingResult) {
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        // Update the product supplier
        ProductSupplier productSupplier = supplierService.updateProductSupplier(request, productSupplierId);
        ProductSupplierResponseDTO productSupplierResponseDTO = new ProductSupplierResponseDTO();
        productSupplierResponseDTO.toDto(productSupplier);
        ResponseData<ProductSupplierResponseDTO> responseData = new ResponseData<>(HttpStatus.OK, "Cập nhật thành công", productSupplierResponseDTO);
        return ResponseEntity.ok(responseData);
    }

    // Endpoint to get a paginated list of all product suppliers
    @GetMapping(value = {"/product-suppliers", "/product-suppliers/"})
    public ResponseEntity<ResponseData<?>> getAllProductSupplier(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            PagedResourcesAssembler<ProductSupplierResponseDTO> assembler) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductSupplierResponseDTO> supplierResponseDTOS = supplierService.getAllProductSupplier(pageable);
        if (supplierResponseDTOS.isEmpty()) {
            ResponseData<PagedModel<EntityModel<ProductSupplierResponseDTO>>> responseData = new ResponseData<>(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm size", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        PagedModel<EntityModel<ProductSupplierResponseDTO>> pagedModel = assembler.toModel(supplierResponseDTOS);
        ResponseData<PagedModel<EntityModel<ProductSupplierResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping(value = {"/product-supplier/{productSupplierId}", "/product-supplier/{productSupplierId}/"})
    public ResponseEntity<ResponseData<?>> deleteProductSupplier(
            @PathVariable(value = "productSupplierId") UUID productSupplierId) {
        boolean isDeleted = supplierService.deleteProductSupplier(productSupplierId);
        if (!isDeleted) {
            ResponseData<?> responseData = new ResponseData<>(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm size", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Xóa thành công", null);
        return ResponseEntity.ok(responseData);
    }
}