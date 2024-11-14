package tdc.edu.vn.project_mobile_be.dtos.requests.cart;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.commond.customexception.UnsupportedOperationException;
import tdc.edu.vn.project_mobile_be.dtos.requests.sizeproduct.SizeProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartCreateRequestDTO implements IDto<Cart> {

    @NotNull(message = "Product id is required")
    @JsonProperty("cartItem")
    private SizeProductRequestParamsDTO sizeProduct;


    @Override
    public Cart toEntity() {
        Cart cart = new Cart();
        BeanUtils.copyProperties(this, cart);
        return cart;
    }


    @Override
    public void toDto(Cart entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
