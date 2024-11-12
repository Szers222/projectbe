package tdc.edu.vn.project_mobile_be.dtos.requests.cart;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProductParamsDTO implements IDto<CartProduct> {

    @NotNull(message = "Product quantity is required")
    @JsonProperty("productQuantity")
    private int productQuantity;

    @NotNull(message = "Product id is required")
    @JsonProperty("productId")
    private SizeProduct sizeProduct;


    @Override
    public CartProduct toEntity() {
        CartProduct cartProduct = new CartProduct();
        BeanUtils.copyProperties(this, cartProduct, "cartProductId");
        return cartProduct;
    }

    @Override
    public void toDto(CartProduct entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
