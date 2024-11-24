package tdc.edu.vn.project_mobile_be.commond;

import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


public class ProductSpecifications implements Specification<Product> {

    private static final Logger logger = LoggerFactory.getLogger(ProductSpecifications.class);

    public static Specification<Product> hasCategory(UUID categoryId) {
        return (root, query, cb) -> {
            // Join giữa Product và Category
            Join<Product, Category> categories = root.join("categories", JoinType.INNER);
            assert query != null;
            Subquery<UUID> subquery = query.subquery(UUID.class);
            Root<Category> categoryRoot = subquery.from(Category.class);
            subquery.select(categoryRoot.get("categoryId"));

            subquery.where(
                    cb.or(
                            cb.equal(categoryRoot.get("parent").get("categoryId"), categoryId),
                            cb.equal(categoryRoot.get("categoryId"), categoryId)
                    )
            );

            return cb.in(categories.get("categoryId")).value(subquery);
        };
    }

    public static Specification<Product> hasSearch(String search) {
        String likePattern = "%" + search + "%";
        return (root, query, cb) -> {
            Join<Product, Category> categories = root.join("categories", JoinType.INNER);
            assert query != null;
            query.distinct(true);
            return cb.or(cb.like(categories.get("categoryName"), likePattern)
                    , cb.like(root.get("productName"), likePattern));
        };
    }


    // Lọc theo khoảng giá
    public static Specification<Product> priceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> cb.between(root.get("productPriceSale"), minPrice, maxPrice);
    }

    // Lọc theo danh sách kích cỡ
    public static Specification<Product> hasSizes(List<UUID> sizeIds) {
        return (root, query, cb) -> root.join("sizeProducts").get("size").get("productSizeId").in(sizeIds);
    }

    // Lọc theo nhà cung cấp (supplier)
    public static Specification<Product> hasSupplier(List<UUID> supplierIds) {
        return (root, query, cb) -> {
            Join<Product, ProductSupplier> suppliers = root.join("supplier", JoinType.INNER);
            return suppliers.get("productSupplierId").in(supplierIds);
        };
    }

    // Sắp xếp
    public static Specification<Product> hasSort(String sort, String direction) {

        return (root, query, cb) -> {

            if (direction.equals("asc")) {
                assert query != null;
                query.orderBy(cb.asc(root.get(sort)));
            } else if (direction.equals("desc")) {
                assert query != null;
                query.orderBy(cb.desc(root.get(sort)));
            } else if (sort.equals("productSale")) {
                assert query != null;
                query.orderBy(cb.desc(root.get("productSale")));
            }
            assert query != null;
            return query.getRestriction();
        };
    }

    @Override
    public Specification<Product> and(Specification<Product> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Product> or(Specification<Product> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
