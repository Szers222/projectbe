package tdc.edu.vn.project_mobile_be.entities.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "product_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;

    @Column(name = "product_id", nullable = false)
    private byte[] productId;

    @Column(name = "action", nullable = false, length = 50)
    private String action;

    @Column(name = "old_data", columnDefinition = "JSON")
    private String oldData;

    @Column(name = "new_data", columnDefinition = "JSON")
    private String newData;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

}
