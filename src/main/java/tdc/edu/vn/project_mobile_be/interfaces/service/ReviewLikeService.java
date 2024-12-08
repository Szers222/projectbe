package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewLikeRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.review.ReviewLikeResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.review.ReviewLike;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface ReviewLikeService extends IService<ReviewLike, UUID> {
    ReviewLike addLike(ReviewLikeRequestDTO request);
    void deleteLike(UUID reviewId, UUID userId);
    List<ReviewLikeResponseDTO> toReviewLike(UUID reviewId);
}
