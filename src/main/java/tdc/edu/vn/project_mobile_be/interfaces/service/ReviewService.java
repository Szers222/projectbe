package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewLikeRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.review.ReviewResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.review.Review;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface ReviewService extends IService<Review, UUID> {
    Review createReview(ReviewCreateRequestDTO request, MultipartFile imageReview);

    List<ReviewResponseDTO> getReviewByProductId(UUID productId);

}
