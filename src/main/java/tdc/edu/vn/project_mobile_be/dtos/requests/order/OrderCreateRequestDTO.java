package tdc.edu.vn.project_mobile_be.dtos.requests.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequestDTO implements IDto<Order> {

    @NotNull(message = "Cart id is required")
    @JsonProperty("cart")
    private UUID cartId;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("userPhone")
    private String userPhone;

    @JsonProperty("userEmail")
    private String userEmail;

    @JsonProperty("userAddress")
    private String userAddress;

    @JsonProperty("userDistrict")
    private String userDistrict;

    @JsonProperty("userCity")
    private String userCity;

    @JsonProperty("userWard")
    private String orderWard;

    @JsonProperty("orderCoupon")
    private List<UUID> orderCoupon;

    @JsonProperty("orderNote")
    private String orderNote;


    @NotNull(message = "Order payment is required")
    @JsonProperty("orderPayment")
    private int orderPayment;



    @Override
    public Order toEntity() {
        Order order = new Order();
        BeanUtils.copyProperties(
                this, order,
                "cartId","userWard","orderPayment","userCity","userDistrict","userAddress","userPhone","userName");
        return order;
    }

    @Override
    public void toDto(Order entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
