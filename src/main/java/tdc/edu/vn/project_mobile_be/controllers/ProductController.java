package tdc.edu.vn.project_mobile_be.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.MultipleFieldsNullOrEmptyException;
import tdc.edu.vn.project_mobile_be.dtos.requests.category.CategoryCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.category.CategoryRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.category.CategoryUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.category.CategoryResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductListeners;
import tdc.edu.vn.project_mobile_be.interfaces.service.CategoryService;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductService;

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
    private CategoryService categoryService;
    @Autowired
    private SimpMessagingTemplate template;


    @PostMapping(value = {"/category", "/category/"})
    public ResponseEntity<ResponseData<?>> createCategory(@RequestBody @Valid CategoryCreateRequestDTO params, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }

        Category createdCategory = categoryService.createCategory(params);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Category tạo thành công !", createdCategory);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }


    @GetMapping("/categories")
    public ResponseEntity<ResponseData<List<CategoryResponseDTO>>> getCategories(@ModelAttribute CategoryRequestParamsDTO params, Pageable pageable) {
        List<CategoryResponseDTO> data = categoryService.getCategories(1, pageable);

        ResponseData<List<CategoryResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success", data);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping({"/category/{categoryId}", "/category/{categoryId}/"})
    public ResponseEntity<ResponseData<?>> deleteCategory(@PathVariable UUID categoryId) {
        boolean isDeleted = categoryService.deleteCategory(categoryId);
        if (isDeleted) {
            ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Category đã được xóa !", null);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.BAD_REQUEST, "Category không tồn tại !", null);
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }

    @PutMapping({"/category/{categoryId}", "/category/"})
    public ResponseEntity<ResponseData<?>> updateCategory(@RequestBody @Valid CategoryUpdateRequestDTO params, @PathVariable UUID categoryId, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)

                    .collect(Collectors.toList());

            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }

        Category createdCategory = categoryService.updateCategory(params, categoryId);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Category update thành công !", createdCategory);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @EventListener(ProductListeners.class)
    @SendTo("/topic/products")
    public void handleProductUpdated(ProductListeners event) {
        Product product = event.getProduct();
        this.template.convertAndSend("/topic/products", product);
    }

}
