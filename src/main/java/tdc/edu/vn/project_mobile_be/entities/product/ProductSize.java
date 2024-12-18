package tdc.edu.vn.project_mobile_be.entities.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_sizes")
@DynamicInsert
@DynamicUpdate
//@EqualsAndHashCode(exclude = "products")
public class ProductSize {

    @Id
    @Column(name = "product_size_id", nullable = false, columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID productSizeId;

    @Column(name = "product_size_name", nullable = false)
    private String productSizeName;

    @Column(name = "product_size_code", nullable = false)
    private String productSizeCode;


    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonManagedReference(value = "product-size")
    private Set<SizeProduct> sizeProducts = new HashSet<>();

    @OneToMany(mappedBy = "productSize", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonManagedReference(value = "cart-product-size")
    private Set<CartProduct> cartProducts = new HashSet<>();

    @OneToMany(mappedBy = "productSize")
    @JsonManagedReference(value = "shipment-product-size")
    @ToString.Exclude
    private Set<ShipmentProduct> shipmentProducts = new HashSet<>();
}