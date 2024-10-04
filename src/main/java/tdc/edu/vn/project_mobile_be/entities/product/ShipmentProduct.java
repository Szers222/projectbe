package tdc.edu.vn.project_mobile_be.entities.product;

import jakarta.persistence.*;
import lombok.Data;
import tdc.edu.vn.project_mobile_be.entities.category.Category;


@Entity
@Table(name = "categorys_products")
@Data
public class CategoryProduct {
    @EmbeddedId
    private CategoryProductId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("product_id");
    @JoinColumn(name = "product_id");
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("category_id");
    @JoinColumn(name = "category_id");
    private Category category;
}
