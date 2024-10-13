package tdc.edu.vn.project_mobile_be.entities.status;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.order.Order;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "order_status")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_status_id", nullable = false,columnDefinition = "BINARY(16)")
    private UUID orderStatusId;
    @Column(name = "order_status_type", nullable = false)
    private int orderStatusType;
    @Column(name = "order_status_name", nullable = false)
    private String orderStatusName;

    @OneToMany(mappedBy = "orderStatus" )
    private Set<Order> oders = new HashSet<>();
}
