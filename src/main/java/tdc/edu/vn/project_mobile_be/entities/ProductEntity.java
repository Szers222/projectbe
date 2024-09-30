package tdc.edu.vn.project_mobile_be.entities;

import lombok.*;
import org.hibernate.annotations.IdGeneratorType;

import java.sql.Timestamp;


@Data
public class ProductEntity {
    private String product_id;
    private String product_name;
    private double product_price;
    private int product_quantity;
    private int product_views;
    private String post_id;
    private String coupon_id;
    private double product_rating;
    private String product_supplier_id;
    private int product_year_of_manufacture;
    private Timestamp created_at;
    private Timestamp updated_at;

}
