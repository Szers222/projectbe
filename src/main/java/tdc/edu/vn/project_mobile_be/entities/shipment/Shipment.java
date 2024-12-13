package tdc.edu.vn.project_mobile_be.entities.shipment;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Table(name = "shipments")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shipment_id", nullable = false,columnDefinition = "BINARY(16)")
    private UUID shipmentId;

    @Column(name = "shipment_date", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Timestamp shipmentDate;

    @Column(name = "shipment_discount", columnDefinition = "FLOAT DEFAULT 0")
    private float shipmentDiscount;

    @Column(name = "shipment_ship_cost", columnDefinition = "FLOAT DEFAULT 0")
    private double shipmentShipCost;



    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    @JsonBackReference(value = "productSupplier-shipment")
    @ToString.Exclude
    private ProductSupplier productSupplier;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "shipment")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ShipmentProduct> shipmentProducts = new HashSet<>();
}
