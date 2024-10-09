package tdc.edu.vn.project_mobile_be.interfaces.service;


import tdc.edu.vn.project_mobile_be.dtos.requests.CreateCategoryRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.CategoryResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface CategoryService extends IService<Category, UUID> {
    Category createCategory(CreateCategoryRequestDTO category);

    List<CategoryResponseDTO> getCategoryTree(int role);
}
