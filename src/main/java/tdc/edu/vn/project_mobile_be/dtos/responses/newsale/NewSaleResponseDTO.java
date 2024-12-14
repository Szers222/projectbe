package tdc.edu.vn.project_mobile_be.dtos.responses.newsale;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.collection.Collection;
import tdc.edu.vn.project_mobile_be.entities.collection.NewSale;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSaleResponseDTO implements IDto<NewSale> {

    @JsonProperty("newsaleId")
    private UUID newSaleId;
    @JsonProperty("imageAlt")
    private String imageAlt;
    @JsonProperty("imageIndex")
    private int imageIndex;
    @JsonProperty("status")
    private int status;
    @JsonProperty("products")
    List<ProductResponseDTO> products;


    @Override
    public NewSale toEntity() {
        return null;
    }

    @Override
    public void toDto(NewSale entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
