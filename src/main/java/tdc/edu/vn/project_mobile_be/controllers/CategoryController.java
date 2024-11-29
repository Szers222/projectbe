package tdc.edu.vn.project_mobile_be.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CategoryRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.CategoryService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;


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
    public ResponseEntity<ResponseData<List<CategoryResponseDTO>>> getCategories(
            @ModelAttribute CategoryRequestParamsDTO params,
            Pageable pageable) {
        List<CategoryResponseDTO> data = categoryService.getCategories(1, pageable);

        ResponseData<List<CategoryResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success",
                data);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping({"/category/{categoryId}", "/category/{categoryId}/"})
    public ResponseEntity<ResponseData<?>> deleteCategory(@PathVariable UUID categoryId) {
        try {
            // Validate and parse the UUID
            categoryService.deleteCategory(categoryId);
            ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Category đã được xóa !", null);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Handle invalid UUID string
            ResponseData<?> responseData = new ResponseData<>(HttpStatus.BAD_REQUEST, "Invalid UUID string: " + categoryId, null);
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle general exceptions
            ResponseData<?> responseData = new ResponseData<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while deleting the category.", null);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping({"/category/{categoryId}", "/category/"})
    public ResponseEntity<ResponseData<?>> updateCategory(
            @RequestBody @Valid CategoryUpdateRequestDTO params,
            @PathVariable UUID categoryId,
            BindingResult bindingResult) {

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

}
