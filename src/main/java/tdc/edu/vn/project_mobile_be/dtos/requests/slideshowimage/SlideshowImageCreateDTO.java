package tdc.edu.vn.project_mobile_be.dtos.requests.slideshowimage;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.slideshow.SlideshowImage;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlideshowImageCreateDTO implements IDto<SlideshowImage> {

    @NotNull(message = "Image ALT is required")
    @JsonProperty("imageAlt")
    private String imageAlt;
    @NotNull(message = "Image index is required")
    @JsonProperty("imageIndex")
    private int imageIndex;
    @NotNull(message = "Product is required")
    @JsonProperty("products")
    private List<UUID> products;


    @Override
    public SlideshowImage toEntity() {
        return null;
    }

    @Override
    public void toDto(SlideshowImage entity) {

    }
}
