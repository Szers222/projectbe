package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.responses.ProductSupplierResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface ProductSupplierService extends IService<ProductSupplier, UUID> {
    List<ProductSupplierResponseDTO> getAllProductSupplier(UUID categoryId);

}
