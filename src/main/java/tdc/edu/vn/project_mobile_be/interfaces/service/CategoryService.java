package tdc.edu.vn.project_mobile_be.interfaces.service;


import org.springframework.data.domain.Pageable;
import tdc.edu.vn.project_mobile_be.dtos.requests.CategoryCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.CategoryRemoveRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.CategoryUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.CategoryResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService extends IService<Category, UUID> {

    Category createCategory(CategoryCreateRequestDTO category);

    List<CategoryResponseDTO> getCategories(int role, Pageable pageable);

    boolean deleteCategory(UUID id);

    Category updateCategory(CategoryUpdateRequestDTO category,UUID id);


}
