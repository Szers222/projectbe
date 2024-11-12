package tdc.edu.vn.project_mobile_be.entities.relationship;

import jakarta.persistence.*;
import lombok.Data;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;

@Entity
@Table(name = "products_sizes")
@Data
public class SizeProduct {
    @EmbeddedId
    private SizeProductId id = new SizeProductId();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("product_size_id")
    @JoinColumn(name = "product_size_id")
    private ProductSize size;

    @Column(name = "sizes_products_quantity", nullable = false, columnDefinition = "int default 0")
    private int quantity;

    @Column(name = "sizes_products_price", nullable = false, columnDefinition = "double default 0")
    private double price;

}
