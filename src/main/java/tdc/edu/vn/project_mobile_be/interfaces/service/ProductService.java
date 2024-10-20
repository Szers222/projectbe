package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.UUID;

public interface ProductService extends IService<Product, UUID> {
    Page<ProductResponseDTO> findProductsByFilters(ProductRequestParamsDTO params, Pageable pageable);

    Product createProduct(ProductCreateRequestDTO params);

    boolean deleteProduct(UUID productId);

    Product updateProduct(ProductUpdateRequestDTO params, UUID productId);
}
