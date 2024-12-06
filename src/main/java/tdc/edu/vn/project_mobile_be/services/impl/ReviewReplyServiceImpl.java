package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewReplyCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.review.ReviewReplyResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.review.Review;
import tdc.edu.vn.project_mobile_be.entities.review.ReviewReply;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ReviewReplyRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ReviewRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ReviewReplyService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewReplyServiceImpl extends AbService<ReviewReply, UUID> implements ReviewReplyService {
    @Autowired
    private final ReviewReplyRepository reviewReplyRepository;
    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public ReviewReplyResponseDTO createReply(ReviewReplyCreateRequestDTO requestDTO) {
        Review review = reviewRepository.findById(requestDTO.getReviewId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy review với ID: " + requestDTO.getReviewId()));
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với ID: " + requestDTO.getUserId()));

        ReviewReply reply = new ReviewReply();
        reply.setReview(review);
        reply.setUser(user);
        reply.setReplyContent(requestDTO.getReplyContent());

        ReviewReply savedReply = reviewReplyRepository.save(reply);

        return ReviewReplyResponseDTO.builder()
                .replyId(savedReply.getReplyId())
                .reviewId(savedReply.getReview().getReviewId())
                .userId(savedReply.getUser().getUserId())
                .replyContent(savedReply.getReplyContent())
                .createdAt(savedReply.getCreatedAt())
                .updatedAt(savedReply.getUpdatedAt())
                .build();
    }

    @Override
    public List<ReviewReplyResponseDTO> getRepliesByReviewId(UUID reviewId) {
        List<ReviewReply> replies = reviewReplyRepository.findByReviewReviewId(reviewId);

        if (replies.isEmpty()) {
            throw new RuntimeException("Không tìm thấy phản hồi nào cho review với ID: " + reviewId);
        }

        return replies.stream()
                .map(reply -> ReviewReplyResponseDTO.builder()
                        .replyId(reply.getReplyId())
                        .reviewId(reply.getReview().getReviewId())
                        .userId(reply.getUser().getUserId())
                        .replyContent(reply.getReplyContent())
                        .createdAt(reply.getCreatedAt())
                        .updatedAt(reply.getUpdatedAt())
                        .build())
                .toList();
    }
}
