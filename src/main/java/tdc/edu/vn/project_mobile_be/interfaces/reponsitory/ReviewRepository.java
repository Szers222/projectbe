package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.review.Review;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    @Query("SELECT r FROM Review r WHERE r.product.productId = :productId")
    List<Review> findByProductId(UUID productId);

    @Query("SELECT r FROM Review r WHERE r.user.userId = :userId")
    List<Review> findByUserId(UUID userId);

    @Query("SELECT r FROM Review r WHERE r.reviewRating >= :rating")
    List<Review> findByRatingGreaterThanEqual(double rating);

    @Query("SELECT r FROM Review r WHERE r.order.orderId = :orderId")
    Review findByOrderId(UUID orderId);
}
