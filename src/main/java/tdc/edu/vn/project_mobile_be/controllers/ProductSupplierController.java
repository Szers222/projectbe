package tdc.edu.vn.project_mobile_be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductSupplierResponseDTO;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductSupplierService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1")
public class ProductSupplierController {
    @Autowired
    private ProductSupplierService supplierService;

    @GetMapping(value = {"/product-suppliers/category/{categoryId}", "/product-sizes/category/{categoryId}/"})
    public ResponseEntity<ResponseData<?>> getAllProductSizesByCategoryID(@PathVariable(value = "categoryId", required = false) UUID categoryId) {

        List<ProductSupplierResponseDTO> supplierResponseDTOS = supplierService.getAllProductSupplier(categoryId);

        ResponseData<List<ProductSupplierResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success", supplierResponseDTOS);
        return ResponseEntity.ok(responseData);
    }
}
