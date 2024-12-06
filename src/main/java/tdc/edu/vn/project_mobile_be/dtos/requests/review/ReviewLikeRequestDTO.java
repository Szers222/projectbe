package tdc.edu.vn.project_mobile_be.dtos.requests.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewLikeRequestDTO {
    @JsonProperty("reviewId")
    @NotNull(message = "review khong null")
    @NotEmpty(message = "khong de trong review")
    private UUID reviewId;
    @JsonProperty("userId")
    @NotNull(message = "user khong null")
    @NotEmpty(message = "khong de trong user")
    private UUID userId;

}
