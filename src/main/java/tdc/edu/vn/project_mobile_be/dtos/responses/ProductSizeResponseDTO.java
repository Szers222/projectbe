package tdc.edu.vn.project_mobile_be.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeResponseDTO implements IDto<ProductSize> {

    @JsonProperty("productSizeId")
    private UUID productSizeId;

    @JsonProperty("productSizeName")
    private String productSizeName;

    @JsonProperty("productSizeType")
    private int productSizeType;

    @JsonProperty("productSizeQuantity")
    private SizeProductResponseDTO sizeProductResponseDTOs;



    @Override
    public ProductSize toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(ProductSize entity) {


        BeanUtils.copyProperties(entity, this);
    }
}
