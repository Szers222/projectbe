package tdc.edu.vn.project_mobile_be.dtos.requests.collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.collection.Collection;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionCreateRequestDTO implements IDto<Collection> {

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
    public Collection toEntity() {
        Collection collection = new Collection();
        BeanUtils.copyProperties(this, collection);
        return collection;
    }

    @Override
    public void toDto(Collection entity) {

    }
}
