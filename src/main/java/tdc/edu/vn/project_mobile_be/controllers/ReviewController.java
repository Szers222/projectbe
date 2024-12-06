package tdc.edu.vn.project_mobile_be.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.review.ReviewCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.review.ReviewResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.review.Review;
import tdc.edu.vn.project_mobile_be.interfaces.service.ReviewService;
import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/reviews")
public class ReviewController {
    @Autowired
    private final ReviewService reviewService;

    @Autowired
    private final ObjectMapper objectMapper;


    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<?>> createReview(
            @RequestPart(value = "image", required = false) MultipartFile imageReview,
            @RequestPart(value = "request", required = true) String requestJson
    ) throws JsonProcessingException {
        ReviewCreateRequestDTO requestDTO = objectMapper.readValue(requestJson, ReviewCreateRequestDTO.class);
        Review review = reviewService.createReview(requestDTO, imageReview);
        ResponseData<?> responseData = new ResponseData<>(
                HttpStatus.CREATED,
                "Review đã được tạo thành công.",
                review
        );
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @PostMapping(value = "/reply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<?>> replyToReview(
            @RequestPart(value = "image", required = false) MultipartFile imageReview,
            @RequestPart(value = "request", required = true) String requestJson
    ) throws JsonProcessingException {
        ReviewCreateRequestDTO requestDTO = objectMapper.readValue(requestJson, ReviewCreateRequestDTO.class);
        if (requestDTO.getParentId() == null) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData<>(
                            HttpStatus.BAD_REQUEST,
                            "Parent ID không được để trống khi phản hồi review.",
                            null
                    ));
        }
        Review reply = reviewService.replyToReview(requestDTO, imageReview);
        ResponseData<?> responseData = new ResponseData<>(
                HttpStatus.CREATED,
                "Phản hồi review đã được tạo thành công.",
                reply
        );

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByProductId(@PathVariable UUID productId) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewByProductId(productId);
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/exists")
    public ResponseEntity<Integer> checkReviewExists(
            @RequestParam UUID orderId,
            @RequestParam UUID productId
    ) {
        boolean exists = reviewService.checkReviewExists(orderId, productId);
        return ResponseEntity.ok(exists ? 1 : 0);
    }

}
