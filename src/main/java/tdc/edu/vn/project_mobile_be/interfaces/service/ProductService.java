package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductService extends IService<Product, UUID> {


    // get all by limit offset
    Page<ProductResponseDTO> findAll(Pageable pageable);

    //    Page<ProductResponseDTO> findAll(Pageable pageable, Sort sort);
    Page<ProductResponseDTO> findAllByCategory(UUID categoryId, Pageable pageable);

    Page<ProductResponseDTO> findProductsPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<ProductResponseDTO> findBySizes(List<UUID> size, Pageable pageable);

    Page<ProductResponseDTO> findByIdSuplier(UUID suplierId, Pageable pageable);

    Page<ProductResponseDTO> findProductsByFilters(ProductRequestParamsDTO params, Pageable pageable);

    List<ProductSupplier> findProductSuppliersByCategory(ProductRequestParamsDTO params);

    List<ProductSize> findProductSizesByCategory(ProductRequestParamsDTO params);
}
