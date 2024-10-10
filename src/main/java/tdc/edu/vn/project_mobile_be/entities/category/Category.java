package tdc.edu.vn.project_mobile_be.entities.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.status.CategoryStatus;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
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
    private UUID id;

    @Transient
    private int level;

    public int getLevel() {
        if (this.parent == null) {
            return 0;
        } else {
            return this.parent.getLevel() + 1;
        }

    }
    @Column(name = "category_name", nullable = false)
    private String name;

    @Column(name = "category_slug", nullable = false)
    private String slug;

    @Column(name = "category_release", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime release;

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
    private CategoryStatus status;

    // Parent - Children
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @ToString.Exclude

    private List<Category> childrens;

    // ManyToMany - Product - Category
//    @ManyToMany(mappedBy = "categories")
//    @ToString.Exclude
//    private Set<Product> products = new HashSet<>();

}
