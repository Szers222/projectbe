package tdc.edu.vn.project_mobile_be.dtos.responses;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.commond.customexception.UnsupportedOperationException;
import tdc.edu.vn.project_mobile_be.entities.status.CategoryStatus;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryStatusResponseDTO implements IDto<CategoryStatus> {
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private int type;
    @JsonIgnore
    private UUID id;

    @Override
    public CategoryStatus toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(CategoryStatus entity) {
        BeanUtils.copyProperties(entity, this, "id");
    }
}
