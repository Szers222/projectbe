package tdc.edu.vn.project_mobile_be.entities.product;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "product_supliers")
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
    @Column(name = "created_at",nullable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String created_at;
    @Column(name = "updated_at",nullable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String updated_at;

    @OneToMany(mappedBy = "suplier",cascade = CascadeType.ALL,orphanRemoval = false)
    private List<Product> products;
}
