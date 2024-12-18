package tdc.edu.vn.project_mobile_be.entities.product;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "product_suppliers")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ProductSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "supplier_id",nullable = false,columnDefinition = "BINARY(16)")
    private UUID productSupplierId;
    @Column(name = "supplier_name",nullable = false)
    private String productSupplierName;
    @Column(name = "supplier_logo")
    private String productSupplierLogo;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "productSupplier", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "productSupplier-shipment")
    private Set<Shipment> shipments = new HashSet<>();

    @OneToMany(mappedBy = "supplier",cascade = CascadeType.ALL,orphanRemoval = true )
    @JsonManagedReference(value = "product-supplier")
    private Set<Product> products = new HashSet<>();

}
