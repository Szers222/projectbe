package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.dtos.requests.productsupplier.ProductSupplierCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productsupplier.ProductSupplierUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductSupplierResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductSupplierRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductSupplierService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    public ProductSupplier createProductSupplier(ProductSupplierCreateRequestDTO request) {
        ProductSupplier productSupplier = request.toEntity();
        productSupplier.setProductSupplierId(UUID.randomUUID());
        return supplierRepository.save(productSupplier);
    }

    @Override
    public ProductSupplier updateProductSupplier(ProductSupplierUpdateRequestDTO request, UUID productSupplierId) {
        Optional<ProductSupplier> productSupplier = supplierRepository.findById(productSupplierId);

        if(productSupplier.isEmpty()){
            throw new EntityNotFoundException("Product Supplier không tìm thấy");
        }

        ProductSupplier productSupplierUpdate = productSupplier.get();
        productSupplierUpdate.setProductSupplierName(request.getProductSupplierName());
        productSupplierUpdate.setProductSupplierLogo(request.getProductSupplierLogo());
        return supplierRepository.save(productSupplierUpdate);
    }

    @Override
    public boolean deleteProductSupplier(UUID productSupplierId) {
        Optional<ProductSupplier> productSupplier = supplierRepository.findById(productSupplierId);

        if(productSupplier.isEmpty()){
            throw new EntityNotFoundException("Product Supplier không tìm thấy");
        }

        supplierRepository.delete(productSupplier.get());
        return true;
    }

    @Override
    public Page<ProductSupplierResponseDTO> getAllProductSupplier(Pageable pageable) {
        Page<ProductSupplier> productSuppliers = supplierRepository.findAll(pageable);
        return productSuppliers.map(productSupplier -> {
            ProductSupplierResponseDTO productSupplierResponseDTO = new ProductSupplierResponseDTO();
            productSupplierResponseDTO.toDto(productSupplier);
            return productSupplierResponseDTO;
        });
    }
}
