package tdc.edu.vn.project_mobile_be.entities.relationship;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;

@Entity
@Table(name = "carts_products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct {
    @EmbeddedId
    private CartProductId id = new CartProductId();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("cart_id")
    @JsonBackReference(value = "cart-product")
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("product_size_id")
    @JoinColumn(name = "product_size_id")
    private ProductSize productSize;


    @Column(name = "carts_products_quantity", nullable = false, columnDefinition = "int default 0")
    private int quantity;
}
