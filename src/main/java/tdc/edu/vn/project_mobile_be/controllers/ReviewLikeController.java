package tdc.edu.vn.project_mobile_be.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewLikeRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.review.ReviewLikeResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.review.ReviewLike;
import tdc.edu.vn.project_mobile_be.interfaces.service.ReviewLikeService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewLikeController {
    @Autowired
    private final ReviewLikeService reviewLikeService;

    @PostMapping(value = "/auth/review-like")
    public ResponseEntity<ResponseData<?>> addReviewLike(@RequestBody ReviewLikeRequestDTO request) {
        ReviewLike reviewLike = reviewLikeService.addLike(request);
        ResponseData<ReviewLike> responseData = new ResponseData<>(
                HttpStatus.CREATED,
                "Thích review thành công",
                reviewLike
        );
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/auth/review-like")
    public ResponseEntity<ResponseData<?>> deleteReviewLike(
            @RequestParam UUID reviewId,
            @RequestParam UUID userId
    ) {
        reviewLikeService.deleteLike(reviewId, userId);
        ResponseData<String> responseData = new ResponseData<>(
                HttpStatus.OK,
                "Bỏ thích review thành công",
                "Đã xóa lượt thích"
        );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @GetMapping(value = "/auth/review-like")
    public ResponseEntity<ResponseData<?>> getReviewLikes(@RequestParam UUID reviewId) {
        List<ReviewLikeResponseDTO> reviewLikes = reviewLikeService.toReviewLike(reviewId);
        ResponseData<List<ReviewLikeResponseDTO>> responseData = new ResponseData<>(
                HttpStatus.OK,
                "Lấy danh sách lượt thích thành công",
                reviewLikes
        );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
