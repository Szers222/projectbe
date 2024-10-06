package tdc.edu.vn.project_mobile_be.entities.product;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "product_images")
@Data
@DynamicUpdate
@DynamicInsert
public class ProductImage {

    @Id
    @Column(name = "product_image_id", nullable = false,columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "product_image_path", nullable = false)
    private String image_path;
    @Column(name = "product_image_alt", nullable = false)
    private String image_alt;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp created_at;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
