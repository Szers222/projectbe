package tdc.edu.vn.project_mobile_be.dtos.responses.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.dtos.responses.cart.CartResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO implements IDto<Order> {

    @JsonProperty("orderId")
    private UUID orderId;
    @JsonProperty("orderStatus")
    private int orderStatus;
    @JsonProperty("orderDate")
    private String orderDate;
    @JsonProperty("orderTotal")
    private double orderTotal;
    @JsonProperty("orderAddress")
    private String orderAddress;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("userPhone")
    private String userPhone;
    @JsonProperty("userEmail")
    private String userEmail;
    @JsonProperty("orderPayment")
    private int orderPayment;
    @JsonProperty("orderNote")
    private String orderNote;
    @JsonProperty("orderShipper")
    private String orderShipper;
    @JsonProperty("orderShipperPhone")
    private String orderShipperPhone;
    @JsonProperty("orderShipperName")
    private String orderShipperName;
    @JsonProperty("orderCouponPerHundred")
    private float orderCouponPerHundred;
    @JsonProperty("orderCouponPrice")
    private double orderCouponPrice;
    @JsonProperty("items")
    private List<CartResponseDTO> items;


    @Override
    public Order toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(Order entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
