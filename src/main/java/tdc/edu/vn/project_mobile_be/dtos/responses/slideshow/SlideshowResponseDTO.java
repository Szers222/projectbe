package tdc.edu.vn.project_mobile_be.dtos.responses.slideshow;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.slideshow.SlideshowImage;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlideshowResponseDTO implements IDto<SlideshowImage> {

    @JsonProperty("id")
    private UUID slideShowImageId;

    @NotNull(message = "Image ALT is required")
    @JsonProperty("imageAlt")
    private String slideShowImageImageAlt;

    @NotNull(message = "Image index is required")
    @JsonProperty("imageIndex")
    private int slideShowImageImageIndex;

    @NotNull(message = "Image URL is required")
    @JsonProperty("imageUrl")
    private String slideShowImageUrl;

    @NotNull(message = "Image is required")
    @JsonProperty("imagePath")
    private String slideShowImageImagePath;

    @JsonProperty("content")
    private String slideShowContent;


    @Override
    public SlideshowImage toEntity() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void toDto(SlideshowImage entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
