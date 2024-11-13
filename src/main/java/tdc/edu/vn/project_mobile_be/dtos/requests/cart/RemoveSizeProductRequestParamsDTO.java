package tdc.edu.vn.project_mobile_be.dtos.requests.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.dtos.requests.sizeproduct.SizeProductRequestParamsDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveSizeProductRequestParamsDTO {
    @NotNull(message = "Product id is required")
    @JsonProperty("cartItem")
    private SizeProductRequestParamsDTO sizeProduct;
}
