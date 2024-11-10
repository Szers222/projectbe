package tdc.edu.vn.project_mobile_be.dtos.requests.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.dtos.requests.coupon.CouponCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.post.PostCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageCreateWithProductRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequestDTO implements IDto<Product> {

    @NotBlank(message = "ProductName không được để trống")
    @JsonProperty("productName")
    private String productName;

    @Min(value = 0, message = "ProductPrice phải lớn hơn hoặc bằng 0")
    @JsonProperty("productPrice")
    private double productPrice;

    @Min(value = 0, message = "ProductQuantity phải lớn hơn hoặc bằng 0")
    @JsonProperty("productQuantity")
    private int productQuantity;

    @JsonProperty("productSale")
    private double productSale;

    @Min(value = 1900, message = "ProductYearOfManufacture không hợp lệ")
    @JsonProperty("productYearOfManufacture")
    private int productYearOfManufacture;

    @JsonIgnore
    private Timestamp createdAt;

    @JsonIgnore
    private Timestamp updatedAt;

    @JsonProperty("productImage")
    private ProductImageCreateWithProductRequestDTO productImageResponseDTOs;


    @JsonProperty("productSizes")
    private List<UUID> sizeIds;

    @JsonProperty("productSupplier")
    private UUID supplierId;

    @JsonProperty("categories")
    private List<UUID> categoryId;

    @JsonProperty("post")
    private PostCreateRequestDTO post;

    @JsonProperty("coupon")
    private CouponCreateRequestDTO coupon;


    @Override
    public Product toEntity() {
        Product product = new Product();
        BeanUtils.copyProperties(this, product, "productImageResponseDTOs", "supplier", "productSizeResponseDTOs", "categories", "createdAt", "updatedAt");
        // Thiết lập ánh xạ thủ công nếu cần
        return product;
    }

    @Override
    public void toDto(Product entity) {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


