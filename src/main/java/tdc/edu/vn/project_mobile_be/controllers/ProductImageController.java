package tdc.edu.vn.project_mobile_be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductImageResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductImageRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductImageService;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = "/api/v1")
@RestController
public class ProductImageController {
    @Autowired
    ProductImageRepository productImageRepository;
    @Autowired
    ProductImageService productImageService;

    @PostMapping(value = {"/product-image", "/product-image/"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<?>> createProducImage(@RequestParam("product-id") UUID productId, @RequestParam("image-alt") String imageAlt, @RequestParam("file") MultipartFile file) {
        ProductImageCreateRequestDTO createProductImageRequestDTO = new ProductImageCreateRequestDTO();
        createProductImageRequestDTO.setProductId(productId);
        createProductImageRequestDTO.setImageAlt(imageAlt);

        ProductImage createdProductImage = productImageService.create(createProductImageRequestDTO, file);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "ProductImage đã được tạo", createdProductImage);
        return new ResponseEntity<ResponseData<?>>(responseData, HttpStatus.CREATED);
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
