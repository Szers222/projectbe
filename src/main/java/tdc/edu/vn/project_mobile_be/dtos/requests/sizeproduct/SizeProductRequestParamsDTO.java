package tdc.edu.vn.project_mobile_be.dtos.requests.sizeproduct;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeProductRequestParamsDTO implements IDto<SizeProduct> {

    @JsonProperty("productQuantity")
    private int productSizeQuantity;

    @JsonProperty("productId")
    private UUID productId;

    @NotNull(message = "Size không được để trống")
    @JsonProperty("sizeId")
    private UUID sizeId;


    @JsonIgnore
    private Product product;
    @JsonIgnore
    private ProductSize size;

    @Override
    public SizeProduct toEntity() {
        SizeProduct sizeProduct = new SizeProduct();
        BeanUtils.copyProperties(this, sizeProduct, "product", "size");
        return sizeProduct;
    }

    @Override
    public void toDto(SizeProduct entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
