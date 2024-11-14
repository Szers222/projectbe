package tdc.edu.vn.project_mobile_be.dtos.requests.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequestDTO implements IDto<Order> {



    @Override
public Order toEntity() {
        Order order = new Order();
        BeanUtils.copyProperties(this, order);
        return order;
    }

    @Override
    public void toDto(Order entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
