package tdc.edu.vn.project_mobile_be.dtos.requests.contentslide;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.contentslide.Content;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentSlideCreateDTO implements IDto<Content> {

    @NotBlank(message = "Content is required")
    @JsonProperty("content")
    private String content;

    @NotNull(message = "Status is required")
    @JsonProperty("status")
    private String status;

    @Override
    public Content toEntity() {
        Content contentSlide = new Content();
        BeanUtils.copyProperties(this, contentSlide, "id");
        return contentSlide;
    }

    @Override
    public void toDto(Content entity) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
