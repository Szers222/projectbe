package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.review.ReviewResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.review.Review;
import tdc.edu.vn.project_mobile_be.entities.review.ReviewLike;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.*;
import tdc.edu.vn.project_mobile_be.interfaces.service.ReviewService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ReviewServiceImp extends AbService<Review, UUID> implements ReviewService {

    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final GoogleCloudStorageService googleCloudStorageService;
    @Autowired
    private final ReviewLikeRepository reviewLikeRepository;

    @Override
    public Review createReview(ReviewCreateRequestDTO reviewCreateRequestDTO, MultipartFile imageReview) {
        Order order = orderRepository.findById(reviewCreateRequestDTO.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order không tồn tại!"));
        Product product = productRepository.findById(reviewCreateRequestDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product không tồn tại!"));
        User user = userRepository.findById(reviewCreateRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại!"));
        Review review = new Review();
        review.setOrder(order);
        review.setProduct(product);
        review.setUser(user);
        review.setReviewComment(reviewCreateRequestDTO.getComment());
        review.setReviewRating(reviewCreateRequestDTO.getRating());
        if (imageReview != null && !imageReview.isEmpty()) {
            String imagePath = saveImage(imageReview);
            review.setReviewImagePath(imagePath);
        }
        Review reviewSave = reviewRepository.save(review);
        updateProductRating(product.getProductId());
        return reviewSave;
    }

    @Override
    public Review replyToReview(ReviewCreateRequestDTO reviewCreateRequestDTO, MultipartFile imageReview) {
        Review parentReview = reviewRepository.findById(reviewCreateRequestDTO.getParentId())
                .orElseThrow(() -> new IllegalArgumentException("Parent review không tồn tại!"));
        User user = userRepository.findById(reviewCreateRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại!"));
        Review reply = new Review();
        reply.setParent(parentReview);
        reply.setUser(user);
        reply.setReviewComment(reviewCreateRequestDTO.getComment());
        if (imageReview != null && !imageReview.isEmpty()) {
            String imagePath = saveImage(imageReview);
            reply.setReviewImagePath(imagePath);
        }
        return reviewRepository.save(reply);
    }

    private String saveImage(MultipartFile image) {
        try {
            return googleCloudStorageService.uploadFile(image);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu ảnh: " + e.getMessage());
        }
    }

    @Override
    public List<ReviewResponseDTO> getReviewByProductId(UUID productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        if (reviews.isEmpty()) {
            throw new RuntimeException("No reviews found for this product");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UUID currentUserId = authentication != null &&
                authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getName())
                ? userRepository.findByUserEmail(authentication.getName())
                .map(User::getUserId)
                .orElse(null)
                : null;
        return reviews.stream()
                .map(review -> {
                    long totalLikes = reviewLikeRepository.countByReview(review);
                    boolean isLiked = currentUserId != null
                            && reviewLikeRepository.existsByReviewIdAndUserId
                            (review.getReviewId(), currentUserId);
                    String userFullName = review.getUser().getUserFirstName() + " " + review.getUser().getUserLastName();
                    return ReviewResponseDTO.builder()
                            .reviewId(review.getReviewId())
                            .comment(review.getReviewComment())
                            .rating((float) review.getReviewRating())
                            .totalLike(totalLikes)
                            .userFullName(userFullName)
                            .children(review.getChildren())
                            .reviewImg(review.getReviewImagePath())
                            .userId(review.getUser().getUserId())
                            .orderId(review.getOrder().getOrderId())
                            .productId(review.getProduct().getProductId())
                            .createdAt(review.getCreatedAt())
                            .updatedAt(review.getUpdatedAt())
                            .isLikedByCurrentUser(isLiked)
                            .build();
                })
                .sorted((r1, r2) -> {
                    // Sắp xếp theo lượt thích giảm dần
                    int compareByLikes = Long.compare(r2.getTotalLike(), r1.getTotalLike());
                    if (compareByLikes != 0) {
                        return compareByLikes;
                    }
                    // Nếu lượt thích bằng nhau, sắp xếp theo ngày tạo mới nhất
                    return r2.getCreatedAt().compareTo(r1.getCreatedAt());
                })
                .toList();
    }



    public boolean checkReviewExists(UUID orderId, UUID productId) {
        return reviewRepository.existsByOrderIdAndProductId(orderId, productId);
    }
    @Override
    public void updateProductRating(UUID productId) {
        Double averageRating = reviewRepository.findAverageRatingByProductId(productId);

        if (averageRating == null) {
            averageRating = 0.0;
        }
        // Làm tròn giá trị đến 2 chữ số thập phân
        BigDecimal roundedAverageRating = BigDecimal.valueOf(averageRating)
                .setScale(2, RoundingMode.HALF_UP);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product không tồn tại!"));

        product.setProductRating(roundedAverageRating.doubleValue());
        productRepository.save(product);
    }
    @Override
    public List<ReviewResponseDTO> getReviewsByProductAndExactRating(UUID productId, double rating) {
        List<Review> reviews = reviewRepository.findByProductIdAndRating(productId, rating);
        if (reviews.isEmpty()) {
            return List.of();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UUID currentUserId = authentication != null &&
                authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getName())
                ? userRepository.findByUserEmail(authentication.getName())
                .map(User::getUserId)
                .orElse(null)
                : null;

        return reviews.stream()
                .map(review -> {
                    long totalLikes = reviewLikeRepository.countByReview(review);
                    boolean isLiked = currentUserId != null &&
                            reviewLikeRepository.existsByReviewIdAndUserId(review.getReviewId(), currentUserId);
                    String userFullName = review.getUser().getUserFirstName() + " " + review.getUser().getUserLastName();
                    return ReviewResponseDTO.builder()
                            .reviewId(review.getReviewId())
                            .comment(review.getReviewComment())
                            .rating((float) review.getReviewRating())
                            .totalLike(totalLikes)
                            .userFullName(userFullName)
                            .children(review.getChildren())
                            .reviewImg(review.getReviewImagePath())
                            .userId(review.getUser().getUserId())
                            .orderId(review.getOrder().getOrderId())
                            .productId(review.getProduct().getProductId())
                            .createdAt(review.getCreatedAt())
                            .updatedAt(review.getUpdatedAt())
                            .isLikedByCurrentUser(isLiked)
                            .build();
                })
                .sorted((r1, r2) -> {
                    // Sắp xếp theo lượt thích giảm dần
                    int compareByLikes = Long.compare(r2.getTotalLike(), r1.getTotalLike());
                    if (compareByLikes != 0) {
                        return compareByLikes;
                    }
                    // Nếu lượt thích bằng nhau, sắp xếp theo ngày tạo mới nhất
                    return r2.getCreatedAt().compareTo(r1.getCreatedAt());
                })
                .toList();
    }


    @Override
    public Review updateReview(UUID reviewId, ReviewUpdateRequestDTO updateRequestDTO, MultipartFile imageReview) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review không tồn tại!"));
        if (updateRequestDTO.getComment() != null) {
            review.setReviewComment(updateRequestDTO.getComment());
        }
        if (updateRequestDTO.getRating() != null) {
            review.setReviewRating(updateRequestDTO.getRating());
        }
        if (imageReview != null && !imageReview.isEmpty()) {
            String imagePath = saveImage(imageReview);
            review.setReviewImagePath(imagePath);
        }
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review không tồn tại!"));
        reviewLikeRepository.deleteByReviewId(reviewId);
        reviewRepository.deleteByParentId(reviewId);
        reviewRepository.delete(review);
        updateProductRating(review.getProduct().getProductId());
    }


}
