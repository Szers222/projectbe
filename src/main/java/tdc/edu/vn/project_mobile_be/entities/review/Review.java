package tdc.edu.vn.project_mobile_be.entities.review;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.sql.Timestamp;
import java.util.*;

@Data
@Entity
@Table(name = "review")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Review {
    @Id
    @Column(name = "review_id",nullable = false,columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID reviewId;

    @Column(name = "review_comment", columnDefinition = "text")
    private String reviewComment = "";

    @Column(name = "review_image_path", columnDefinition = "VARCHAR(255)")
    private String reviewImagePath = "";

    @Column(name = "review_rating", nullable = false, columnDefinition = "double default 0")
    private double reviewRating = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "order-review")
    @JoinColumn(name = "order_id")
    private Order order = null;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "product-review")
    @JoinColumn(name = "product_id")
    private Product product = null;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "user-review")
    @JoinColumn(name = "user_id")
    private User user = null;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ReviewLike> reviewLikes = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Review parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Review> children = new ArrayList<>();


    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;
}

