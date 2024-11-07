package tdc.edu.vn.project_mobile_be.dtos.requests.productsupplier;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.commond.customexception.UnsupportedOperationException;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSupplierCreateRequestDTO implements IDto<ProductSupplier> {
    @NotBlank(message = "product-supplier-name không được để trống")
    @JsonProperty("productSupplierName")
    private String productSupplierName;
    @NotBlank(message = "product-supplier-logo không được để trống")
    @JsonProperty("productSupplierLogo")
    private String productSupplierLogo;

    @Override
    public ProductSupplier toEntity() {
        ProductSupplier productSupplier = new ProductSupplier();
        BeanUtils.copyProperties(this, productSupplier, "productSupplierId");
        return productSupplier;
    }

    @Override
    public void toDto(ProductSupplier entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
