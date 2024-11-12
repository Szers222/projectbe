package tdc.edu.vn.project_mobile_be.entities.relationship;

import jakarta.persistence.*;
import lombok.Data;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;

@Entity
@Table(name = "carts_products")
@Data

public class CartProduct {
    @EmbeddedId
    private CartProductId id = new CartProductId();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("cart_id")
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "carts_products_quantity", nullable = false, columnDefinition = "int default 0")
    private int quantity;
}
