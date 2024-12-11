package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.review.Review;
import tdc.edu.vn.project_mobile_be.entities.review.ReviewLike;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, UUID> {
    boolean existsByReviewAndUser(Review review, User user);
    Optional<ReviewLike> findByReviewAndUser(Review review, User user);
    long countByReview(Review review);
}
