package tdc.edu.vn.project_mobile_be.dtos.requests.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewUpdateRequestDTO {
    @JsonProperty("comment")
    private String comment;
    @Min(value = 0, message = "khong dung gia tri rating")
    @Max(value = 5, message = "Khong dung gia tri rating")
    @JsonProperty("rating")
    private Double rating;
}
