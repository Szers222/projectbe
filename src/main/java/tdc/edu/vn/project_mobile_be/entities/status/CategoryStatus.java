package tdc.edu.vn.project_mobile_be.entities.status;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.category.Category;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@Entity
@Table(name = "category_status")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class CategoryStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_status_id", nullable = false,columnDefinition = "BINARY(16)")
    private UUID categoryStatusId;
    @Column(name = "category_status_type", nullable = false)
    private int categoryStatusType;
    @Column(name = "category_status_name", nullable = false)
    private String categoryStatusName;

    @OneToMany(mappedBy = "categoryStatus")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Category> categories = new HashSet<>();
}
