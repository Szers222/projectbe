package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductSizeRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductSizeResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductSizeRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductSizeService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductSizeServiceImpl extends AbService<ProductSize, UUID> implements ProductSizeService {

    public final int PRODUCT_CLOTHES = 0;
    public final int PRODUCT_SHOES = 1;
    public final int PRODUCT_ACCESSORIES = 2;


    @Autowired
    private ProductSizeRepository productSizeRepository;


    @Override
    public List<ProductSizeResponseDTO> getAllProductSize(ProductSizeRequestParamsDTO productSizeRequestParamsDTO) {
        List<ProductSize> productSizes = productSizeRepository.findAllByProductId(productSizeRequestParamsDTO.getIds());
        System.out.println(productSizes.size());
        if (productSizes.isEmpty()) {
            throw new EntityNotFoundException("Product size not found");
        }

        return productSizes.stream().map(productSize -> {
            ProductSizeResponseDTO dto = new ProductSizeResponseDTO();
            dto.toDto(productSize);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductSizeResponseDTO> getAllProductSizeByCategoryID(UUID categoryId) {
        if (categoryId == null) {
            throw new EntityNotFoundException("Category not found");
        }
        List<ProductSize> productSizes = productSizeRepository.findProductSizesByCategory(categoryId);
        System.console().printf("productSizes: " + productSizes);
        if (productSizes.isEmpty()) {
            throw new EntityNotFoundException("Product Supplier not found");
        }
        List<ProductSizeResponseDTO> result = new ArrayList<>();
        productSizes.stream().forEach(productSize -> {
            ProductSizeResponseDTO productSizeResponseDTO = new ProductSizeResponseDTO();
            productSizeResponseDTO.toDto(productSize);
            result.add(productSizeResponseDTO);
        });
        if (result.isEmpty()) {
            throw new ListNotFoundException("List product size not found");
        }
        return result;
    }
}
