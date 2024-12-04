package tdc.edu.vn.project_mobile_be.dtos.requests.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequestDTO {
    @NotNull(message = "order khong null")
    @JsonProperty("orderId")
    private UUID orderId;
    @NotNull(message = "userId khong nulll")
    @JsonProperty("userId")
    private UUID userId;
    @JsonProperty("comment")
    private String comment;
    @NotNull(message = "ranting khong null")
    @Min(value = 0, message = "khong dung gia tri rating")
    @Max(value = 5, message = "Khong dung gia tri rating")
    @JsonProperty("rating")
    private Double rating;
    @NotNull(message = "Product khong null")
    @JsonProperty("productId")
    private UUID productId;
}
