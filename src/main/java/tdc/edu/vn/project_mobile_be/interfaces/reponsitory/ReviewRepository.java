package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.review.Review;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    @Query("SELECT r FROM Review r WHERE r.product.productId = :productId")
    List<Review> findByProductId(UUID productId);

    @Query("SELECT AVG(r.reviewRating) FROM Review r WHERE r.product.productId = :productId")
    Double findAverageRatingByProductId(@Param("productId") UUID productId);
    @Query("SELECT r FROM Review r WHERE r.product.productId = :productId AND r.reviewRating = :rating")
    List<Review> findByProductIdAndRating(@Param("productId") UUID productId, @Param("rating") double rating);


    @Modifying
    @Query("DELETE FROM Review r WHERE r.parent.reviewId = :parentId")
    void deleteByParentId(@Param("parentId") UUID parentId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Review r WHERE r.order.orderId = :orderId AND r.product.productId = :productId")
    boolean existsByOrderIdAndProductId(@Param("orderId") UUID orderId, @Param("productId") UUID productId);
}
