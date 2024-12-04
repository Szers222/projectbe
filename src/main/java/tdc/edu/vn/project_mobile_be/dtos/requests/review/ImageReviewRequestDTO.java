package tdc.edu.vn.project_mobile_be.dtos.requests.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageReviewRequestDTO {
    @JsonProperty("userImagePath")
    MultipartFile imageReview;
}
