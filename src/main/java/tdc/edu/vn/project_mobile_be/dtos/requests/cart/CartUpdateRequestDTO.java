package tdc.edu.vn.project_mobile_be.dtos.requests.cart;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateRequestDTO implements IDto<Cart> {

    @JsonProperty("cartItem")
    private List<CartProductParamsDTO> cartProductParamsDTO;

    @Override
    public Cart toEntity() {
        return null;
    }

    @Override
    public void toDto(Cart entity) {

    }
}
