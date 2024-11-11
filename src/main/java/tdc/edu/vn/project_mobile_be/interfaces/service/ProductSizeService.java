package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductSizeRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productsize.ProductSizeCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productsize.ProductSizeUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductSizeResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface ProductSizeService extends IService<ProductSize, UUID> {

    List<ProductSizeResponseDTO> getAllProductSizeByProductId(ProductSizeRequestParamsDTO productSizeRequestParamsDTO);

    List<ProductSizeResponseDTO> getAllProductSizeByCategoryID(UUID categoryId);

    Page<ProductSizeResponseDTO> getAllProductSize(Pageable pageable);

    ProductSize createProductSize(ProductSizeCreateRequestDTO request);

    ProductSize updateProductSize(ProductSizeUpdateRequestDTO request, UUID productSizeId);

    boolean deleteProductSize(UUID productSizeId);
}
