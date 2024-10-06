package tdc.edu.vn.project_mobile_be.entities.shipment;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Table(name = "shipments")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shipment_id", nullable = false,columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "shipment_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp date;
    @Column(name = "shipment_discount", columnDefinition = "FLOAT DEFAULT 0")
    private float discount;
    @Column(name = "shipment_ship_cost", columnDefinition = "FLOAT DEFAULT 0")
    private float ship_cost;
    @Column(name = "shipment_supplier", nullable = false, columnDefinition = "VARCHAR(255)")
    private String supplier;


    @OneToMany(mappedBy = "shipment",cascade = CascadeType.ALL,orphanRemoval = false)
    private Set<ShipmentProduct> shipmentProducts = new HashSet<>();
}
