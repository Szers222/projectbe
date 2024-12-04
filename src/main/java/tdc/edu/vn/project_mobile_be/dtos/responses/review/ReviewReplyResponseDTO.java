package tdc.edu.vn.project_mobile_be.dtos.responses.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewReplyResponseDTO {
    @JsonProperty("replyId")
    private UUID replyId;

    @JsonProperty("reviewId")
    private UUID reviewId;

    @JsonProperty("userId")
    private UUID userId;

    @JsonProperty("replyContent")
    private String replyContent;

    @JsonProperty("createdAt")
    private Timestamp createdAt;

    @JsonProperty("updatedAt")
    private Timestamp updatedAt;
}
