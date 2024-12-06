package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewReplyCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.review.ReviewReplyResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.review.ReviewReply;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface ReviewReplyService extends IService<ReviewReply, UUID> {
    ReviewReplyResponseDTO createReply(ReviewReplyCreateRequestDTO requestDTO);

    List<ReviewReplyResponseDTO> getRepliesByReviewId(UUID reviewId);

}
