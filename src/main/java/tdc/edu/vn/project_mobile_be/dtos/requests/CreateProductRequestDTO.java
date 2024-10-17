package tdc.edu.vn.project_mobile_be.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequestDTO implements IDto<Product> {

    @JsonProperty("productName")
    private String productName;
    @JsonProperty("postContent")
    private String postContent;

    @Override
    public Product toEntity() {
        return null;
    }

    @Override
    public void toDto(Product entity) {

    }
}
