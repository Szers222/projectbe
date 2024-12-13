package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.product.ProductUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface ProductService extends IService<Product, UUID> {
    Page<ProductResponseDTO> findProductsByFilters(ProductRequestParamsDTO params, Pageable pageable);

    Product createProduct(ProductCreateRequestDTO params, MultipartFile[] file);

    void deleteProduct(UUID productId);

    Product updateProduct(ProductUpdateRequestDTO params, UUID productId, MultipartFile[] file);


    List<ProductResponseDTO> getProductByCategoryId(UUID categoryId);

    ProductResponseDTO getProductById(UUID productId);

    List<ProductResponseDTO> findProductRelate(UUID productId);

    List<ProductResponseDTO> getProductNew();

}
