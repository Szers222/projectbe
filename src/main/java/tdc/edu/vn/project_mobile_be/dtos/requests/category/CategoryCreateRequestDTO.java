package tdc.edu.vn.project_mobile_be.dtos.requests.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryCreateRequestDTO implements IDto<Category> {

    @NotBlank(message = "Tên danh mục không được để trống")
    @JsonProperty("categoryName")
    private String categoryName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    @JsonProperty("categoryRelease")
    private LocalDate categoryRelease;

    @NotNull(message = "StatusId không được để trống")
    @JsonProperty("statusId")
    private UUID statusId;

    @NotBlank(message = "Ảnh danh mục không được để trống")
    @JsonProperty("categoryImgPath")
    private String categoryImgPath;

    @JsonProperty("categoryParent")
    private UUID parentId = null;

    @JsonIgnore
    private Timestamp createdAt;
    @JsonIgnore
    private Timestamp updatedAt;
    @JsonIgnore
    private LocalDate deletedAt;
    @JsonIgnore
    private Category categoryParent;
    @JsonIgnore
    private Category categoryChildren;


    @Override
    public Category toEntity() {
        Category category = new Category();

        BeanUtils.copyProperties(this, category, "id", "createdAt", "updatedAt");

        return category;
    }


    @Override
    public void toDto(Category entity) {
        throw new UnsupportedOperationException("Unimplemented method 'toDTO' in CategoryCreateRequestDTO");
    }

}
