package tdc.edu.vn.project_mobile_be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateCategoryRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.CategoryResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CategoryRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.CategoryService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(value = {"/category", "/category/"})
    public ResponseEntity<ResponseData<?>> createCategory(@RequestBody CreateCategoryRequestDTO createCategoryRequestDTO) {
        Category createdCategory = categoryService.createCategory(createCategoryRequestDTO);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Category tạo thành công !", createdCategory);
        return new ResponseEntity<ResponseData<?>>(responseData, HttpStatus.CREATED);
    }

    @GetMapping(value = {"/categories", "/categories/"})
    public ResponseEntity<ResponseData<?>> getAllCategories() {

        return null;
    }
    @GetMapping("/tree")
    public ResponseEntity<ResponseData<List<CategoryResponseDTO>>> getCategoryTree() {
        List<CategoryResponseDTO> data = categoryService.getCategoryTree(1);
        ResponseData<List<CategoryResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success",
                data);
        return ResponseEntity.ok(responseData);
    }
}
