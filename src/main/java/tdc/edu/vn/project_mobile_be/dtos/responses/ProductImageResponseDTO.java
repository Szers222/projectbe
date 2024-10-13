package tdc.edu.vn.project_mobile_be.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageResponseDTO implements IDto<ProductImage> {

    @JsonProperty("productImagePath")
    private String productImagePath;
    @JsonProperty("productImageAlt")
    private String productImageAlt;
    @JsonProperty("productImageIndex")
    private int productImageIndex;
    @JsonIgnore
    private UUID productId;
    @JsonIgnore
    private UUID id;
    @JsonIgnore
    private Timestamp createdAt;
    @JsonIgnore
    private Timestamp updatedAt;



    @Override
    public ProductImage toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(ProductImage entity) {
        BeanUtils.copyProperties(entity, this, "id", "productId", "createdAt", "updatedAt");
    }
}
