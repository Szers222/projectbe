package tdc.edu.vn.project_mobile_be.dtos.responses.collection;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.collection.Collection;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionResponseDTO implements IDto<Collection> {

    @JsonProperty("collectionId")
    private UUID collectionId;
    @JsonProperty("imageAlt")
    private String imageAlt;
    @JsonProperty("imageUrl")
    private String imageUrl;
    @JsonProperty("imageIndex")
    private int imageIndex;
    @JsonProperty("products")
    List<ProductResponseDTO> products;


    @Override
    public Collection toEntity() {
        return null;
    }

    @Override
    public void toDto(Collection entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
