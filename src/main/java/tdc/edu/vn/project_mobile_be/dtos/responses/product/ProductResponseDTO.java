package tdc.edu.vn.project_mobile_be.dtos.responses.product;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.dtos.responses.category.CategoryResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.post.PostResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO implements IDto<Product> {
    @JsonProperty("productId")
    private UUID productId;
    @JsonProperty("productName")
    private String productName;
    @JsonProperty("productPrice")
    private String productPrice;
    @JsonProperty("productQuantity")
    private int productQuantity;
    @JsonProperty("productViews")
    private int productViews;
    @JsonProperty("productRating")
    private double productRating;
    @JsonProperty("productSale")
    private double productSale;
    @JsonProperty("categories")
    private List<CategoryResponseDTO> categoryResponseDTO;
    @JsonProperty("productYearOfManufacture")
    private int productYearOfManufacture;
    @JsonProperty("productPriceSale")
    private String productPriceSale;
    @JsonIgnore
    private Timestamp createdAt;
    @JsonIgnore
    private Timestamp updatedAt;
    @JsonProperty("productImages")
    private List<ProductImageResponseDTO> productImageResponseDTOs;
    @JsonProperty("productSupplier")
    private ProductSupplierResponseDTO supplier;
    @JsonProperty("productSizes")
    private List<ProductSizeResponseDTO> productSizeResponseDTOs;
    @JsonProperty("post")
    private PostResponseDTO postResponseDTO;

    @Override
    public Product toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(Product entity) {
//        this.productImageResponseDTOs = new ArrayList<>();
//
//        this.categoryResponseDTO = entity.getCategories().stream().map(category -> {
//            CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
//            categoryResponseDTO.toDto(category);
//            return categoryResponseDTO;
//        }).toList();
//
//        this.productSizeResponseDTOs = entity.getSizes().stream().map(productSize -> {
//            ProductSizeResponseDTO productSizeResponseDTO = new ProductSizeResponseDTO();
//            productSizeResponseDTO.toDto(productSize);
//            return productSizeResponseDTO;
//        }).toList();
//
//        ProductSupplierResponseDTO productSupplierResponse = new ProductSupplierResponseDTO();
//        productSupplierResponse.toDto(entity.getSupplier());
//        this.supplier = productSupplierResponse;
//
//
//        for (ProductImage productImage : entity.getImages()) {
//            ProductImageResponseDTO productImageResponseDTO = new ProductImageResponseDTO();
//            productImageResponseDTO.toDto(productImage);
//            productImageResponseDTOs.add(productImageResponseDTO);
//        }
//
//        PostResponseDTO postResponseDTO = new PostResponseDTO();
//        postResponseDTO.toDto(entity.getPost());
//        this.postResponseDTO = postResponseDTO;


        BeanUtils.copyProperties(entity, this, "createdAt", "updatedAt");
//        this.productPrice = this.formatPrice(entity.getProductPrice());
//        double productPriceSale = entity.getProductPrice() - (entity.getProductPrice() * this.productSale / 100);
//        this.productPriceSale = this.formatPrice(productPriceSale);
    }


}
