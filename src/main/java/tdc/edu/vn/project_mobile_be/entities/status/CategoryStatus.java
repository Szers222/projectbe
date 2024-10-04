package tdc.edu.vn.project_mobile_be.entities.status;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.category.Category;


import java.util.List;
import java.util.UUID;


@Data
@Entity
@Table(name = "category_status")
@DynamicInsert
@DynamicUpdate
public class CategoryStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_status_id", nullable = false,columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "category_status_type", nullable = false)
    private int type;
    @Column(name = "category_status_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Category> categoryList;
}
