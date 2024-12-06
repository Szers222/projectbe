package tdc.edu.vn.project_mobile_be.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewReplyCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.review.ReviewReplyResponseDTO;
import tdc.edu.vn.project_mobile_be.interfaces.service.ReviewReplyService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewReplyController {

    @Autowired
    private final ReviewReplyService reviewReplyService;

    @PostMapping("/auth/reviews/replies")
    public ResponseEntity<ResponseData<ReviewReplyResponseDTO>> createReply(
            @RequestBody @Valid ReviewReplyCreateRequestDTO requestDTO) {
        ReviewReplyResponseDTO reply = reviewReplyService.createReply(requestDTO);

        ResponseData<ReviewReplyResponseDTO> responseData = new ResponseData<>(
                HttpStatus.CREATED,
                "Phản hồi đã được tạo thành công",
                reply
        );

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @GetMapping("/auth/reviews/{reviewId}/replies")
    public ResponseEntity<List<ReviewReplyResponseDTO>> getRepliesByReviewId(@PathVariable UUID reviewId) {
        List<ReviewReplyResponseDTO> replies = reviewReplyService.getRepliesByReviewId(reviewId);
        return ResponseEntity.ok(replies);
    }
}

