package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.review.ReviewResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.review.Review;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.*;
import tdc.edu.vn.project_mobile_be.interfaces.service.ReviewService;

import java.io.IOException;
import java.util.List;
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

        return reviews.stream()
                .map(review -> {
                    long totalLikes = reviewLikeRepository.countByReview(review);
                    String userFullName = review.getUser().getUserFirstName() + " " + review.getUser().getUserLastName();
                    return ReviewResponseDTO.builder()
                            .reviewId(review.getReviewId())
                            .comment(review.getReviewComment())
                            .rating((float) review.getReviewRating())
                            .totalLike(totalLikes)
                            .userFullName(userFullName)
                            .children(review.getChildren())
                            .orderId(review.getOrder().getOrderId()) // Thêm orderId
                            .productId(review.getProduct().getProductId()) // Thêm productId
                            .createdAt(review.getCreatedAt())
                            .updatedAt(review.getUpdatedAt())
                            .build();
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
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product không tồn tại!"));

        product.setProductRating(averageRating);
        productRepository.save(product);
    }
    @Override
    public List<ReviewResponseDTO> getReviewsByProductAndExactRating(UUID productId, double rating) {
        List<Review> reviews = reviewRepository.findByProductIdAndRating(productId, rating);

        // Trả về danh sách rỗng nếu không có review nào
        return reviews.stream()
                .map(review -> {
                    long totalLikes = reviewLikeRepository.countByReview(review);
                    String userFullName = review.getUser().getUserFirstName() + " " + review.getUser().getUserLastName();
                    return ReviewResponseDTO.builder()
                            .reviewId(review.getReviewId())
                            .comment(review.getReviewComment())
                            .rating((float) review.getReviewRating())
                            .totalLike(totalLikes)
                            .userFullName(userFullName)
                            .children(review.getChildren())
                            .orderId(review.getOrder().getOrderId())
                            .productId(review.getProduct().getProductId())
                            .createdAt(review.getCreatedAt())
                            .updatedAt(review.getUpdatedAt())
                            .build();
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
        reviewRepository.delete(review);
        updateProductRating(review.getProduct().getProductId());
    }


}
