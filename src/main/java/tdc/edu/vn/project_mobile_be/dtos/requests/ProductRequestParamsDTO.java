package tdc.edu.vn.project_mobile_be.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestParamsDTO {
    private int page = 0;
    private int size = 20;
    private String sort = "productPrice";
    private String direction = "asc";
    private UUID categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<UUID> sizeIds;
    private UUID supplierId;
    private String search;
}
