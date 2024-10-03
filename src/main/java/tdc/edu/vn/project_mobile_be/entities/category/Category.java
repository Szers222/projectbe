package tdc.edu.vn.project_mobile_be.entities.category;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "categories", schema = "relationship")
@DynamicInsert
@DynamicUpdate
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String name;
    private String slug;
    private String status_id;
    private Timestamp release;
    private String parent_id;
    private Timestamp created_at;
    private Timestamp updated_at;
}
