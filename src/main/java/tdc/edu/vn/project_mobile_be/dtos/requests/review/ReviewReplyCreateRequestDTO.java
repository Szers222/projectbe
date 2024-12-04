package tdc.edu.vn.project_mobile_be.dtos.requests.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReplyCreateRequestDTO {
    @NotNull(message = "reviewId không được null")
    @JsonProperty("reviewId")
    private UUID reviewId;

    @NotNull(message = "userId không được null")
    @JsonProperty("userId")
    private UUID userId;

    @NotNull(message = "replyContent không được null")
    @JsonProperty("replyContent")
    private String replyContent;
}
