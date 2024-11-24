package tdc.edu.vn.project_mobile_be.dtos.requests.product;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductRequestParamsDTO {
    private int page = 0;
    private int size = 20;
    private String sort = "productPriceSale";
    private String direction = "asc";
    private UUID categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<UUID> sizeIds;
    private List<UUID> supplierIds;
    private String search;
}
