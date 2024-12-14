package tdc.edu.vn.project_mobile_be.entities.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.status.CategoryStatus;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
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

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "category_release", nullable = false, columnDefinition = "Timestamp")
    private Timestamp categoryRelease;

    @Column(name = "category_img_path", columnDefinition = "TEXT")
    private String categoryImgPath;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "deletion_date", columnDefinition = "DATE")
    private LocalDate deletionDate;
    /**
     * ManyToOne
     */
    @ManyToOne
    @JoinColumn(name = "category_status_id")
    @JsonBackReference(value = "category-status")
    private CategoryStatus categoryStatus;

    // Parent - Children
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference(value = "parent-children")
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference(value = "parent-children")
    private List<Category> children;

    // ManyToMany - Product - Category
    @ManyToMany(mappedBy = "categories")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Set<Product> products = new HashSet<>();
}
