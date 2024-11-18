package tdc.edu.vn.project_mobile_be.dtos.requests.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.dtos.requests.coupon.CouponUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.post.PostUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageParamsWithProductRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageUpdateWithProductRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.sizeproduct.SizeProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductSizeResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductSupplierResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequestDTO implements IDto<Product> {

    @NotBlank(message = "ProductName không được để trống")
    @JsonProperty("productName")
    private String productName;

    @JsonIgnore
    private double productPrice;

    @JsonIgnore
    private int productQuantity;

    @JsonIgnore
    private double productSale;


    @NotNull(message = "Category không được để null")
    @JsonProperty("categories")
    private List<UUID> categoryId;


    @NotNull(message = "ProductSupplier không được để null")
    @JsonProperty("productSupplier")
    private UUID productSupplier;


    @NotNull(message = "SizeProduct không được để null")
    @JsonProperty("sizesProduct")
    private List<SizeProductRequestParamsDTO> sizesProduct;

    @Min(value = 1900, message = "ProductYearOfManufacture không hợp lệ")
    @JsonProperty("productYearOfManufacture")
    private int productYearOfManufacture;

    @JsonIgnore
    private Timestamp createdAt;

    @JsonIgnore
    private Timestamp updatedAt;

    @JsonProperty("productImage")
    private ProductImageParamsWithProductRequestDTO productImageResponseDTOs;

    @JsonIgnore
    private ProductSupplierResponseDTO supplier;

    @JsonIgnore
    private List<ProductSizeResponseDTO> productSizeResponseDTOs;

    @JsonProperty("post")
    private PostUpdateRequestDTO post;

    @JsonProperty("coupon")
    private CouponUpdateRequestDTO coupon;

    @Override
    public Product toEntity() {
        Product product = new Product();
        BeanUtils.copyProperties(this, product, "productImageResponseDTOs", "supplier", "productSizeResponseDTOs");
        // Thiết lập ánh xạ thủ công nếu cần
        return product;
    }

    @Override
    public void toDto(Product entity) {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


