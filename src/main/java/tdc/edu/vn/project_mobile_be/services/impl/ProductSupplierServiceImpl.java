package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductSupplierResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductSupplierRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductSupplierService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductSupplierServiceImpl extends AbService<ProductSupplier, UUID> implements ProductSupplierService {

    @Autowired
    private ProductSupplierRepository supplierRepository;

    @Override
    public List<ProductSupplierResponseDTO> getAllProductSupplier(UUID categoryId) {
        List<ProductSupplierResponseDTO> result = new ArrayList<>();
        if (categoryId != null) {
            List<ProductSupplier> productSuppliers = supplierRepository.findProductSupplierByCategory(categoryId);

            if (productSuppliers.isEmpty()) {
                throw new EntityNotFoundException("Product Supplier not found");
            }

            productSuppliers.stream().toList().forEach(productSupplier -> {
                ProductSupplierResponseDTO productSupplierResponseDTO = new ProductSupplierResponseDTO();
                productSupplierResponseDTO.toDto(productSupplier);
                result.add(productSupplierResponseDTO);
            });
            if (result.isEmpty()) {
                throw new ListNotFoundException("List product size not found");
            }
        }
        if (categoryId == null || categoryId.equals("")) {
            List<ProductSupplier> productSuppliers = supplierRepository.findAll();

            if (productSuppliers.isEmpty()) {
                throw new EntityNotFoundException("Product Supplier not found");
            }

            productSuppliers.stream().toList().forEach(productSupplier -> {
                ProductSupplierResponseDTO productSupplierResponseDTO = new ProductSupplierResponseDTO();
                productSupplierResponseDTO.toDto(productSupplier);
                result.add(productSupplierResponseDTO);
            });
            if (result.isEmpty()) {
                throw new ListNotFoundException("List product size not found");
            }
        }

        return result;
    }
}
