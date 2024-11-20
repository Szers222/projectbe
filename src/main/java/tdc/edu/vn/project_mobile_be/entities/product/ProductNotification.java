package tdc.edu.vn.project_mobile_be.entities.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "product_notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductNotification {
    @Id
    @Column(name = "notification_id", nullable = false, columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productNotification;

    @Column(name = "product_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID productId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "product_price", nullable = false)
    private double productPrice;
    @Column(name = "product_sale", nullable = false)
    private double productSale;
    @Column(name = "product_quantity", nullable = false)
    private int productQuantity;
}
