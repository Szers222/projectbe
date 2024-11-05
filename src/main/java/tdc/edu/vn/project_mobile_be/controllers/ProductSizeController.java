package tdc.edu.vn.project_mobile_be.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductSizeRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductSizeResponseDTO;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductSizeService;

import java.util.List;
import java.util.UUID;

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
    @GetMapping(value = {"/product-sizes", "/product-sizes/"})
    public ResponseEntity<ResponseData<?>> getAllProductSizes() {

        List<ProductSizeResponseDTO> productSizes = productSizeService.getAllProductSize();

        ResponseData<List<ProductSizeResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success", productSizes);
        return ResponseEntity.ok(responseData);
    }
    @GetMapping(value = {"/product-sizes/category/{categoryId}", "/product-sizes/category"})
    public ResponseEntity<ResponseData<?>> getAllProductSizesByCategoryID(@PathVariable(value = "categoryId",required = false) UUID categoryId) {

            List<ProductSizeResponseDTO> productSizes = productSizeService.getAllProductSizeByCategoryID(categoryId);

            ResponseData<List<ProductSizeResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success", productSizes);
            return ResponseEntity.ok(responseData);
    }
}
