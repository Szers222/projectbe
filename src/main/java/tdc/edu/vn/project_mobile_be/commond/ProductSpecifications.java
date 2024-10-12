package tdc.edu.vn.project_mobile_be.commond;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProductSpecifications {

    public static Specification<Product> hasCategory(UUID categoryId) {
        return (root, query, cb) -> {
            // Join giữa Product và Category
            Join<Product, Category> categories = root.join("categories", JoinType.INNER);
            return cb.equal(categories.get("id"), categoryId);
        };
    }


    // Lọc theo khoảng giá
    public static Specification<Product> priceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> cb.between(root.get("price"), minPrice, maxPrice);
    }

    // Lọc theo danh sách kích cỡ
    public static Specification<Product> hasSizes(List<UUID> sizeIds) {
        return (root, query, cb) -> root.join("sizes").get("id").in(sizeIds);
    }

    // Lọc theo nhà cung cấp (supplier)
    public static Specification<Product> hasSupplier(UUID supplierId) {

        return (root, query, cb) -> {
            // Join giữa Product và Category
            Join<Product, ProductSupplier> suppliers = root.join("suplier", JoinType.INNER);
            return cb.equal(suppliers.get("id"), supplierId);
        };
    }
}
