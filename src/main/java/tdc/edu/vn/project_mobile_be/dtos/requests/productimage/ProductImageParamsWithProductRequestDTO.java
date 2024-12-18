package tdc.edu.vn.project_mobile_be.dtos.requests.productimage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageParamsWithProductRequestDTO implements IDto<ProductImage> {

    @JsonIgnore
    private String productImagePath;

    @NotNull(message = "product-image-alt không được để trống")
    @JsonProperty("productImageAlt")
    private String productImageAlt;



    @JsonIgnore
    private Timestamp createdAt;
    @JsonIgnore
    private Timestamp updatedAt;

    @Override
    public ProductImage toEntity() {
        ProductImage productImage = new ProductImage();
        BeanUtils.copyProperties(this, productImage, "id", "createdAt", "updatedAt", "imagePath");
        return productImage;
    }

    @Override
    public void toDto(ProductImage entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
