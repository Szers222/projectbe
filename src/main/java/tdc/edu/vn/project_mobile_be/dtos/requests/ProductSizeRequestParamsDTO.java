package tdc.edu.vn.project_mobile_be.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeRequestParamsDTO {
    private List<UUID> ids;
}
