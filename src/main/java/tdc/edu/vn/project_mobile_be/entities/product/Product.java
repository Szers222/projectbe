package tdc.edu.vn.project_mobile_be.entities.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.entities.collection.Collection;
import tdc.edu.vn.project_mobile_be.entities.collection.NewSale;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Table(name = "products")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
//@EqualsAndHashCode(exclude = {"sizes", "images"})

public class Product {
    @Id
    @Column(name = "product_id", nullable = false,columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID productId;

    @Column(name = "product_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String productName;

    @Column(name = "product_price", nullable = false)
    private double productPrice;

    @Column(name = "product_quantity", nullable = false, columnDefinition = "int default 0")
    private int productQuantity;
    @Column(name = "product_sale", columnDefinition = "double default 0")
    private double productSale;
    @Column(name = "product_views", columnDefinition = "int default 0")
    private int productViews;

    @Column(name = "product_rating", columnDefinition = "double default 0")
    private double productRating;

    @Column(name = "product_year_of_manufacture", columnDefinition = "int default 2000")
    private int productYearOfManufacture;

    @Formula("product_price - (product_price * product_sale / 100)")
    private double productPriceSale;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "coupon_id", referencedColumnName = "coupon_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Coupon coupon;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "size-product")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ProductImage> images = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference(value = "product-supplier")
    @JoinColumn(name = "supplier_id")
    private ProductSupplier supplier;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "categories_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Collection> collections = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<NewSale> newsales = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonManagedReference(value = "shipment-product")
    @EqualsAndHashCode.Exclude
    private Set<ShipmentProduct> shipmentProducts = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference(value = "product-cart")
    private Set<CartProduct> cartProducts = new HashSet<>();


    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonManagedReference(value = "size-product")
    @EqualsAndHashCode.Exclude
    private Set<SizeProduct> sizeProducts = new HashSet<>();

}
