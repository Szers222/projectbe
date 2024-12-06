package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewCreateRequestDTO;
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
    public Review createReview(ReviewCreateRequestDTO request, MultipartFile imageReview) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order không tồn tại"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product không tồn tại"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        if (order.getOrderStatus() != 5) {
            throw new RuntimeException("Chỉ những đơn hàng có trạng thái '5' mới được phép đánh giá");
        }
        String imagePath = null;
        try {
            if (imageReview != null && !imageReview.isEmpty()) {
                imagePath = googleCloudStorageService.uploadFile(imageReview);
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload ảnh: " + e.getMessage());
        }
        Review review = new Review();
        review.setReviewComment(request.getComment());
        review.setReviewRating(request.getRating());
        review.setProduct(product);
        review.setUser(user);
        review.setOrder(order);
        review.setReviewImagePath(imagePath);
        return reviewRepository.save(review);
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

}
