package tdc.edu.vn.project_mobile_be.entities.relationship;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;

@Entity
@Table(name = "products_sizes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SizeProduct {
    @EmbeddedId
    private SizeProductId id = new SizeProductId();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("product_id")
    @JsonBackReference(value = "size-product")
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("product_size_id")
    @JsonBackReference(value = "product-size")
    @JoinColumn(name = "product_size_id",nullable = false)
    private ProductSize size;

    @Column(name = "sizes_products_quantity", columnDefinition = "int default 0")
    private int quantity;

}
