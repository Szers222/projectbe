package tdc.edu.vn.project_mobile_be.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductImageRequestDTO implements IDto<ProductImage> {

    @JsonIgnore
    private String imagePath;

    @NotBlank(message = "product-image-alt không được để trống")
    @JsonProperty("product-image-alt")
    private String imageAlt;

    @NotBlank(message = "product-id không được để trống")
    @JsonProperty("product-id")
    private UUID productId;
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