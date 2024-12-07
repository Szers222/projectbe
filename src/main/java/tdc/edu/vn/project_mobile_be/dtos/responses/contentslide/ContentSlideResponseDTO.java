package tdc.edu.vn.project_mobile_be.dtos.responses.contentslide;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.contentslide.Content;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentSlideResponseDTO implements IDto<Content> {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("status")
    private String status;

    @Override
    public Content toEntity() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void toDto(Content entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
