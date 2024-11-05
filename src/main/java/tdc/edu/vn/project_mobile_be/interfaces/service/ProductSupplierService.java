package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tdc.edu.vn.project_mobile_be.dtos.requests.productsupplier.ProductSupplierCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productsupplier.ProductSupplierUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductSupplierResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface ProductSupplierService extends IService<ProductSupplier, UUID> {
    List<ProductSupplierResponseDTO> getAllProductSupplier(UUID categoryId);

    Page<ProductSupplierResponseDTO> getAllProductSupplier(Pageable pageable);

    ProductSupplier createProductSupplier(ProductSupplierCreateRequestDTO request);

    ProductSupplier updateProductSupplier(ProductSupplierUpdateRequestDTO request, UUID productSupplierId);

    boolean deleteProductSupplier(UUID productSupplierId);



}
