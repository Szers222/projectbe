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
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductImageResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductImageRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductImageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping(value = "/api/v1")
@RestController
public class ProductImageController {
    @Autowired
    ProductImageRepository productImageRepository;
    @Autowired
    ProductImageService productImageService;

    @PostMapping(value = {"/product-image", "/product-image/"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ResponseData<?>>> createProducImage(
            @Valid @ModelAttribute ProductImageCreateRequestDTO params,
            @RequestParam("file") MultipartFile[] files,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        if (files.length == 0) {
            throw new ListNotFoundException("File is empty");
        }
        ProductImage createdProductImage = new ProductImage();
        ResponseData<?> responseData;
        List<ResponseData<?>> responseDataList = new ArrayList<>();
        for (MultipartFile file : files) {
            createdProductImage = productImageService.createProductImage(params, file);
            responseData = new ResponseData<>(HttpStatus.CREATED, "ProductImage đã được tạo", createdProductImage);
            responseDataList.add(responseData);
        }


        return new ResponseEntity<List<ResponseData<?>>>(responseDataList,HttpStatus.CREATED);
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
