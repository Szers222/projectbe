package tdc.edu.vn.project_mobile_be.dtos.requests.slideshowimage;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.entities.slideshow.SlideshowImage;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlideshowImageCreateDTO implements IDto<SlideshowImage> {

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
    private MultipartFile imagePath;


    @Override
    public SlideshowImage toEntity() {
        SlideshowImage enitty = new SlideshowImage();
        BeanUtils.copyProperties(this, enitty);
        return enitty;
    }

    @Override
    public void toDto(SlideshowImage entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
