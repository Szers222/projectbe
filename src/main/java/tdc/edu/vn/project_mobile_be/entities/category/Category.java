package tdc.edu.vn.project_mobile_be.entities.category;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ManyToAny;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.status.CategoryStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "categories", schema = "relationship")
@DynamicInsert
@DynamicUpdate
public class Category {
    @Id
    @Column(name = "category_id", nullable = false,columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @Column(name = "category_slug", nullable = false)
    private String slug;

    @Column(name = "category_release", nullable = false)
    private Timestamp release;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at",nullable = false)
    private Timestamp updated_at;

    /**
     * ManyToOne
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_status_id", nullable = false)
    private CategoryStatus status;

    // Parent - Children
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", nullable = false)
    private Category parent;
    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL,orphanRemoval = false)
    private List<Category> childrens;

    // ManyToMany - Product - Category
    @ManyToMany(mappedBy = "categories")
    @ToString.Exclude
    private List<Product> products;

}
