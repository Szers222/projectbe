package tdc.edu.vn.project_mobile_be.dtos.requests.newsale;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.collection.NewSale;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSaleUpdateRequestDTO implements IDto<NewSale> {


    @NotNull(message = "Image ALT is required")
    @JsonProperty("imageAlt")
    private String imageAlt;
    @NotNull(message = "Image index is required")
    @JsonProperty("imageIndex")
    private int imageIndex;
    @NotNull(message = "Product is required")
    @JsonProperty("products")
    private List<UUID> products;

    @JsonProperty("status")
    private int status;

    @Override
    public NewSale toEntity() {
        NewSale newsale = new NewSale();
        BeanUtils.copyProperties(this, newsale);
        return newsale;
    }

    @Override
    public void toDto(NewSale entity) {

    }
}
