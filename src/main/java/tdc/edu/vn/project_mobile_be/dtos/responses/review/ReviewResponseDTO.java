package tdc.edu.vn.project_mobile_be.dtos.responses.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponseDTO {
    @JsonProperty("reviewId")
    UUID reviewId;

    @JsonProperty("comment")
    String comment;

    @JsonProperty("rating")
    float rating;

    @JsonProperty("order")
    Order order;

    @JsonProperty("totalLike")
    long totalLike;

    @JsonProperty("createdAt")
    Timestamp createdAt;

    @JsonProperty("updatedAt")
    Timestamp updatedAt;


}
