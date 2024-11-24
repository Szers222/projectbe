package tdc.edu.vn.project_mobile_be.dtos.requests.productsize;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeCreateRequestDTO implements IDto<ProductSize> {

    @NotBlank(message = "product-size-name không được để trống")
    @JsonProperty("productSizeName")
    private String productSizeName;

    @NotNull(message = "Product Size Code không được để trống")
    @Column(name = "product_size_code")
    private String productSizeCode;

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
