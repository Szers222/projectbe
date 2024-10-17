package tdc.edu.vn.project_mobile_be.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;



public class CategoryRemoveRequestDTO extends RemoveByIdRequestDTO implements IDto<Category> {

    @Override
    public Category toEntity() {
        Category category = new Category();

        BeanUtils.copyProperties(this, category, "categoryParent", "categoryStatus", "categoryChildren");

        return category;
    }

    @Override
    public void toDto(Category entity) {
        throw new UnsupportedOperationException("Unimplemented method 'toDTO' in CategoryRemoveRequestDTO");
    }
}
