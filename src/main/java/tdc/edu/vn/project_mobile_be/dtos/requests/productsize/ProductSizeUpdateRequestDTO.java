package tdc.edu.vn.project_mobile_be.dtos.requests.productsize;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeUpdateRequestDTO implements IDto<ProductSize> {
    @NotBlank(message = "product-size-name không được để trống")
    @JsonProperty("productSizeName")
    private String productSizeName;
    @NotBlank(message = "product-size-type không được để trống")
    @JsonProperty("productSizeType")
    private int productSizeType;

    @Override
    public ProductSize toEntity() {
        ProductSize productSize = new ProductSize();
        BeanUtils.copyProperties(this, productSize, "productSizeId");
        return productSize;
    }

    @Override
    public void toDto(ProductSize entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
