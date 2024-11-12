package tdc.edu.vn.project_mobile_be.dtos.responses.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSupplierResponseDTO implements IDto<ProductSupplier> {

    @JsonProperty("productSupplierSd")
    private UUID productSupplierId;
    @JsonProperty("productSupplierName")
    private String productSupplierName;
    @JsonProperty("productSupplierType")
    private int productSupplierType;

    @Override
    public ProductSupplier toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(ProductSupplier entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
