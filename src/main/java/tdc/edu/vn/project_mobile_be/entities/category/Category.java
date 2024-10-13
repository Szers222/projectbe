package tdc.edu.vn.project_mobile_be.entities.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.status.CategoryStatus;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "categories", schema = "relationship")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate

public class Category {
    @Id
    @Column(name = "category_id", nullable = false,columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID categoryId;

    @Transient
    private int categoryLevel;

    public int getLevel() {
        if (this.parent == null) {
            return 0;
        } else {
            return this.parent.getLevel() + 1;
        }

    }
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "category_slug", nullable = false)
    private String categorySlug;

    @Column(name = "category_release", nullable = false, columnDefinition = "TIMESTAMP")
    private ZonedDateTime categoryRelease;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

    /**
     * ManyToOne
     */
    @ManyToOne
    @JoinColumn(name = "category_status_id", nullable = false)
    @JsonBackReference
    private CategoryStatus categoryStatus;

    // Parent - Children
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Category> children;

    // ManyToMany - Product - Category
    @ManyToMany(mappedBy = "categories")
    @ToString.Exclude
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<Product> products = new HashSet<>();

}
