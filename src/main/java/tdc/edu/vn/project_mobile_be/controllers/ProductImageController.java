package tdc.edu.vn.project_mobile_be.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.MultipleFieldsNullOrEmptyException;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductImageResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductImageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping(value = "/api/v1")
@RestController
public class ProductImageController {

    @Autowired
    ProductImageService productImageService;

    @PostMapping(value = {"/product-image", "/product-image/"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ResponseData<?>>> createProductImage(
            @Valid @ModelAttribute ProductImageCreateRequestDTO params,
            @RequestParam("file") MultipartFile[] files,
            BindingResult bindingResult) {

        // Kiểm tra lỗi đầu vào
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }

        // Kiểm tra files không bị null hoặc rỗng
        if (files == null || files.length == 0) {
            throw new ListNotFoundException("No files provided");
        }

        List<ResponseData<?>> responseDataList = new ArrayList<>();
        List<ProductImage> createdProductImages = productImageService.createProductImage(params, files);
        if (createdProductImages.isEmpty()) {
            throw new ListNotFoundException("No product images created");
        }
        // Duyệt qua từng file và tạo ProductImage
        for (ProductImage productImage : createdProductImages) {
            ResponseData<ProductImage> responseData = new ResponseData<>(
                    HttpStatus.CREATED,
                    "ProductImage đã được tạo",
                    productImage
            );
            responseDataList.add(responseData);
        }
        return new ResponseEntity<>(responseDataList, HttpStatus.CREATED);
    }

    @PutMapping(value = {"/product-image/{productImageId}", "/product-image/{productImageId}/"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<?>> updateProductImage(
            @Valid @ModelAttribute ProductImageUpdateRequestDTO params,
            @RequestParam("file") MultipartFile file,
            @PathVariable("productImageId") UUID productImageId,
            BindingResult bindingResult) {

        // Kiểm tra lỗi đầu vào
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }

        // Kiểm tra file không bị null
        if (file == null) {
            throw new ListNotFoundException("No file provided");
        }

        ProductImage updatedProductImage = productImageService.updateProductImage(params, file, productImageId);
        ResponseData<ProductImage> responseData = new ResponseData<>(
                HttpStatus.OK,
                "ProductImage đã được cập nhật",
                updatedProductImage
        );
        return ResponseEntity.ok(responseData);
    }
    @DeleteMapping(value = {"/product-image/{productImageId}", "/product-image/{productImageId}/"})
    public ResponseEntity<ResponseData<?>> deleteProductImage(@PathVariable("productImageId") UUID productImageId) {
        boolean isDeleted = productImageService.deleteProductImage(productImageId);
        if (!isDeleted) {
            throw new ListNotFoundException("ProductImage not found");
        }
        ResponseData<Boolean> responseData = new ResponseData<>(HttpStatus.OK, "ProductImage đã được xóa", true);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = {"/product-images", "/product-images/"})
    public ResponseEntity<ResponseData<?>> getAllProductImageByProductId(@RequestParam("product-id") UUID productId) {
        System.out.println("productId: " + productId);
        List<ProductImageResponseDTO> data = productImageService.findAllByProductId(productId);
        ResponseData<List<ProductImageResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success",
                data);
        return ResponseEntity.ok(responseData);
    }
}
