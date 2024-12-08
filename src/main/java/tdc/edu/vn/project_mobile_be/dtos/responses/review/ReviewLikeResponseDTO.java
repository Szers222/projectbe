package tdc.edu.vn.project_mobile_be.dtos.responses.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewLikeResponseDTO {
    @JsonProperty("reviewId")
    UUID reviewId;
    @JsonProperty("userId")
    UUID userId;
}
