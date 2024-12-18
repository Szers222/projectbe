package tdc.edu.vn.project_mobile_be.dtos.responses.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.commond.customexception.UnsupportedOperationException;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProduct;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductResponseDTO implements IDto<CartProduct> {

    @JsonProperty("productId")
    private UUID productId;

    @JsonProperty("productSizeId")
    private UUID productSizeId;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("productImage")
    private String productImage;

    @JsonProperty("productSize")
    private String productSize;

    @JsonProperty("productQuantity")
    private int cartProductQuantity;

    @JsonProperty("productPrice")
    private String cartProductPrice;

    @JsonProperty("productDiscount")
    private double cartProductDiscount;

    @JsonProperty("productDiscountPrice")
    private String cartProductDiscountPrice;

    @JsonProperty("productTotalPrice")
    private double cartProductTotalPrice;


    @Override
    public CartProduct toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(CartProduct entity) {
        BeanUtils.copyProperties(entity, this, "id");
    }
}
