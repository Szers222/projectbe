package tdc.edu.vn.project_mobile_be.entities.product;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "product_supliers")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ProductSuplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_suplier_id",nullable = false,columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "product_suplier_name",nullable = false)
    private String name;
    private String description;
    @Column(name = "product_suplier_logo",nullable = false)
    private String logo;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "suplier",cascade = CascadeType.ALL,orphanRemoval = false)
    private Set<Product> products = new HashSet<>();
}
