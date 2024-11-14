package tdc.edu.vn.project_mobile_be.dtos.requests.cart;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.dtos.requests.sizeproduct.SizeProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateRequestDTO implements IDto<Cart> {

    @NotNull(message = "Product id is required")
    @JsonProperty("cartItem")
    private SizeProductRequestParamsDTO sizeProduct;

    @Override
    public Cart toEntity() {
        return null;
    }

    @Override
    public void toDto(Cart entity) {

    }
}
