package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductNotification  implements Serializable {
    private String action;  // CREATE, UPDATE, DELETE
    private UUID productId;
    private String productName;
    private double productPrice;
    private int productQuantity;
    private double productSale;
    private double productRating;
    private Set<String> categories;
    private Set<String> sizes;
    private Timestamp timestamp;

    public static ProductNotification fromProduct(String action, Product product) {
        return ProductNotification.builder()
                .action(action)
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productQuantity(product.getProductQuantity())
                .productSale(product.getProductSale())
                .productRating(product.getProductRating())
                .categories(product.getCategories().stream().map(Category::getCategoryName).collect(Collectors.toSet())).sizes(product.getSizes().stream().map(ProductSize::getProductSizeName).collect(Collectors.toSet())).timestamp(new Timestamp(System.currentTimeMillis())).build();
    }
}
