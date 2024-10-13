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

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.*;

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
    @JsonProperty("productCategories")
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
    private Set<ProductImageResponseDTO> productImageResponseDTOs;
    @JsonProperty("productSupplier")
    private ProductSupplierResponseDTO supplier;
    @JsonProperty("productSizes")
    private List<ProductSizeResponseDTO> productSizeResponseDTOs;


    @Override
    public Product toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(Product entity) {
        this.productImageResponseDTOs = new HashSet<>();
        this.categoryResponseDTO = entity.getCategories().stream().map(category -> {
            CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
            categoryResponseDTO.toDto(category);
            return categoryResponseDTO;
        }).toList();
        this.productSizeResponseDTOs = entity.getSizes().stream().map(productSize -> {
            ProductSizeResponseDTO productSizeResponseDTO = new ProductSizeResponseDTO();
            productSizeResponseDTO.toDto(productSize);
            return productSizeResponseDTO;
        }).toList();
        ProductSupplierResponseDTO productSupplierResponse = new ProductSupplierResponseDTO();
        productSupplierResponse.toDto(entity.getSupplier());
        this.supplier = productSupplierResponse;
        for (ProductImage productImage : entity.getImages()) {
            ProductImageResponseDTO productImageResponseDTO = new ProductImageResponseDTO();
            productImageResponseDTO.toDto(productImage);
            productImageResponseDTOs.add(productImageResponseDTO);
        }

        BeanUtils.copyProperties(entity, this, "createdAt", "updatedAt");
        this.productPrice = this.formatPrice(entity.getProductPrice());
        double productPriceSale = entity.getProductPrice() - (entity.getProductPrice() * this.productSale / 100);
        this.productPriceSale = this.formatPrice(productPriceSale);
    }

    public static String formatPrice(double price) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        format.setRoundingMode(RoundingMode.HALF_EVEN);
        return format.format(price);
    }
}
