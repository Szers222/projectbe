package tdc.edu.vn.project_mobile_be.entities.relationship;

import jakarta.persistence.*;
import lombok.Data;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;


@Entity
@Table(name = "shipments_products")
@Data

public class ShipmentProduct {
    @EmbeddedId
    private ShipmentProductId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("shipment_id")
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    @Column(name = "shipments_products_quantity", nullable = false, columnDefinition = "int default 0")
    private int quantity;
    @Column(name = "shipments_products_price", nullable = false, columnDefinition = "double default 0")
    private double price;
}
