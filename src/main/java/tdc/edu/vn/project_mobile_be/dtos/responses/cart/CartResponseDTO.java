package tdc.edu.vn.project_mobile_be.dtos.responses.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.commond.customexception.UnsupportedOperationException;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO implements IDto<Cart> {

    @JsonProperty("cartId")
    private UUID cartId;

    @JsonProperty("cartItem")
    List<CartProductResponseDTO> cartProducts;

    @JsonProperty("productTotalPrice")
    private String cartProductTotalPrice;

    @JsonProperty("productQuantity")
    private int cartProductQuantity;


    @Override
    public Cart toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(Cart entity) {
        BeanUtils.copyProperties(entity, this, "id");
    }
}
