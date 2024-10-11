package tdc.edu.vn.project_mobile_be.dtos.responses;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO implements IDto<Product> {
    @JsonProperty("product-id")
    private UUID id;
    @JsonProperty("product-name")
    private String name;
    @JsonProperty("product-price")
    private double price;
    @JsonProperty("product-quantity")
    private int quantity;
    @JsonProperty("product-views")
    private int views;
    @JsonProperty("product-rating")
    private double rating;
    @JsonProperty("product-sale")
    private double sale;
    @JsonIgnore
    private Timestamp createdAt;
    @JsonIgnore
    private Timestamp updatedAt;
    @JsonProperty("product-images")
    private Set<ProductImageResponseDTO> productImageResponseDTOs;


    @Override
    public Product toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(Product entity) {
        this.productImageResponseDTOs = new HashSet<>();
        for (ProductImage productImage : entity.getImages()) {
            ProductImageResponseDTO productImageResponseDTO = new ProductImageResponseDTO();
            productImageResponseDTO.toDto(productImage);
            productImageResponseDTOs.add(productImageResponseDTO);
        }
        BeanUtils.copyProperties(entity, this, "createdAt", "updatedAt");
    }
}
