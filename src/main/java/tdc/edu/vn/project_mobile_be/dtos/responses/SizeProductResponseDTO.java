package tdc.edu.vn.project_mobile_be.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeProductResponseDTO implements IDto<SizeProduct> {
    @JsonProperty("productSizeQuantity")
    private int productSizeQuantity;
    @JsonIgnore
    private Product product;
    @JsonIgnore
    private ProductSize size;

    @Override
    public SizeProduct toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(SizeProduct entity) {
        BeanUtils.copyProperties(entity, this, "id");
    }
}
