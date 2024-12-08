package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.review.Review;
import tdc.edu.vn.project_mobile_be.entities.review.ReviewLike;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, UUID> {
    boolean existsByReviewAndUser(Review review, User user);
    Optional<ReviewLike> findByReviewAndUser(Review review, User user);
    long countByReview(Review review);
    List<ReviewLike> findAllByReview(Review review);
    @Query("SELECT CASE WHEN COUNT(rl) > 0 THEN true ELSE false END " +
            "FROM ReviewLike rl WHERE rl.review.reviewId = :reviewId AND rl.user.userId = :userId")
    boolean existsByReviewIdAndUserId(@Param("reviewId") UUID reviewId, @Param("userId") UUID userId);
    @Modifying
    @Query("DELETE FROM ReviewLike rl WHERE rl.review.reviewId = :reviewId")
    void deleteByReviewId(@Param("reviewId") UUID reviewId);
}
