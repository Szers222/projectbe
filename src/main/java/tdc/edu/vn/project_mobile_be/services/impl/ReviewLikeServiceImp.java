package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewLikeRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.review.ReviewLikeResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.review.Review;
import tdc.edu.vn.project_mobile_be.entities.review.ReviewLike;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ReviewLikeRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ReviewRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ReviewLikeService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewLikeServiceImp extends AbService<ReviewLike, UUID> implements ReviewLikeService {

    @Autowired
    private final ReviewLikeRepository reviewLikeRepository;
    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public ReviewLike addLike(ReviewLikeRequestDTO request) {
        Review review = reviewRepository.findById(request.getReviewId())
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy review với ID: " +
                        request.getReviewId()));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy user với ID: " +
                        request.getUserId()));

        if (reviewLikeRepository.existsByReviewAndUser(review, user)) {
            throw new RuntimeException("User đã thích review này trước đó.");
        }

        ReviewLike reviewLike = new ReviewLike();
        reviewLike.setReview(review);
        reviewLike.setUser(user);

        return reviewLikeRepository.save(reviewLike);
    }

    @Override
    public void deleteLike(UUID reviewId, UUID userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy review với ID: "
                        + reviewId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy user với ID: "
                        + userId));
        ReviewLike reviewLike = reviewLikeRepository.findByReviewAndUser(review, user)
                .orElseThrow(() -> new RuntimeException
                        ("Không tìm thấy lượt thích cho review này"));
        reviewLikeRepository.delete(reviewLike);
    }

    @Override
    public List<ReviewLikeResponseDTO> toReviewLike(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy review với ID: " + reviewId));
        List<ReviewLike> reviewLikes = reviewLikeRepository.findAllByReview(review);
        if (reviewLikes.isEmpty()) {
            throw new RuntimeException("Không có lượt thích nào cho review này");
        }
        return reviewLikes.stream()
                .map(reviewLike -> ReviewLikeResponseDTO.builder()
                        .reviewId(review.getReviewId())
                        .userId(reviewLike.getUser().getUserId())
                        .build())
                .collect(Collectors.toList());
    }



}
