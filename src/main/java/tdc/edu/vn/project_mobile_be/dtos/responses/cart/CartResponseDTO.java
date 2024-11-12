package tdc.edu.vn.project_mobile_be.dtos.responses.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO implements IDto<Cart> {


    @JsonProperty("cartProduct")
    private CartProductResponseDTO cartProduct;


    @Override
    public Cart toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(Cart entity) {

    }
}
