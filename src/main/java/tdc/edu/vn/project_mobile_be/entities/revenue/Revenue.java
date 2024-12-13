package tdc.edu.vn.project_mobile_be.entities.revenue;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Table(name = "revenues")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Revenue {
    @Id
    @Column(name = "revenue_id", nullable = false,columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID revenueId;
    @Column(name = "revenue_total", nullable = false)
    private double revenueTotal;
    @Column(name = "revenue_date", nullable = false)
    private Timestamp revenueDate;
}
