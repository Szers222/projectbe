package tdc.edu.vn.project_mobile_be.dtos.requests.sizeproduct;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeProductRequestParamsDTO implements IDto<SizeProduct> {

    @JsonProperty("productSizeQuantity")
    private int productSizeQuantity;
    @JsonProperty("productSizeId")
    private List<UUID> productSizeId;

    @JsonIgnore
    private Product product;
    @JsonIgnore
    private ProductSize size;

    @Override
    public SizeProduct toEntity() {
        return null;
    }

    @Override
    public void toDto(SizeProduct entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
