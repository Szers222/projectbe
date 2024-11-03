package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.UUID;

public interface ProductService extends IService<Product, UUID> {
    Page<tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductResponseDTO> findProductsByFilters(ProductRequestParamsDTO params, Pageable pageable);

    Product createProduct(ProductCreateRequestDTO params);

    boolean deleteProduct(UUID productId);

    Product updateProduct(ProductUpdateRequestDTO params, UUID productId);


    Page<tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductResponseDTO> getProductByCategoryId(UUID categoryId, Pageable pageable);

    tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductResponseDTO getProductById(UUID productId);

    Page<tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductResponseDTO> findProductRelate(UUID productId, Pageable pageable);


}
