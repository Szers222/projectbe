package tdc.edu.vn.project_mobile_be.dtos.requests.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequestDTO {
    @JsonProperty("orderId")
    private UUID orderId;
    @JsonProperty("userId")
    private UUID userId;
    @JsonProperty("comment")
    private String comment;
    @Min(value = 0, message = "khong dung gia tri rating")
    @Max(value = 5, message = "Khong dung gia tri rating")
    @JsonProperty("rating")
    private Double rating;
    @JsonProperty("productId")
    private UUID productId;
    @JsonProperty("userImagePath")
    MultipartFile imageReview;
    @JsonProperty("parentId")
    private UUID parentId;
}
