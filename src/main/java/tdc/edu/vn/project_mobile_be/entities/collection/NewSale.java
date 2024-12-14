package tdc.edu.vn.project_mobile_be.entities.collection;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.product.Product;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "newsales")
public class NewSale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "newsale_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID newSaleId;

    @Column(name = "newsale_status", nullable = false, columnDefinition = "int default 0")
    private Integer newSaleStatus;

    @Column(name = "newsale_index", nullable = false, columnDefinition = "INTEGER")
    private Integer newSaleIndex;

    @Column(name = "newsale_alt", length = 255, columnDefinition = "VARCHAR(255)")
    private String newSaleAlt;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinTable(
            name = "newsales_products",
            joinColumns = @JoinColumn(name = "newsale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonBackReference
    Set<Product> products = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;
}
