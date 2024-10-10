package tdc.edu.vn.project_mobile_be.dtos.responses;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.commond.customexception.UnsupportedOperationException;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO implements IDto<Category> {


    @JsonProperty("category-name")
    private String name;


    @JsonProperty("category-slug")
    private String slug;


    @JsonProperty("category-release")
    private ZonedDateTime release;

    @JsonProperty("category-parent")
    private UUID parentId = null;
    @JsonProperty("category-level")
    private int level;
    @JsonProperty("status")
    private CategoryStatusResponseDTO status;

    @JsonProperty("childrens")
    private List<CategoryResponseDTO> childrens;

    @JsonIgnore
    private Timestamp createdAt;

    @JsonIgnore
    private Timestamp updatedAt;


    @Override
    public Category toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public void toDto(Category entity) {
        BeanUtils.copyProperties(entity, this, "id", "createdAt", "updatedAt", "status", "parent", "childrens");

        if (entity.getStatus() != null) {
            this.status = new CategoryStatusResponseDTO();
            this.status.toDto(entity.getStatus());
        }
        this.parentId = entity.getParent() != null ? entity.getParent().getId() : null;
        this.childrens = entity.getChildrens().stream()
                .map(child -> {
                    CategoryResponseDTO childDto = new CategoryResponseDTO();
                    childDto.toDto(child);
                    return childDto;
                })
                .collect(Collectors.toList());
    }
}
