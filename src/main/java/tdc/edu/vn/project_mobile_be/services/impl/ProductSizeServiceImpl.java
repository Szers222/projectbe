package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductSizeRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductSizeResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.SizeProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductSizeRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.SizeProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductSizeService;

import java.util.HashSet;
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
    @Autowired
    private SizeProductRepository sizeProductRepository;

    @Override
    public List<ProductSizeResponseDTO> getAllProductSize(ProductSizeRequestParamsDTO productSizeRequestParamsDTO) {
        List<UUID> productIds = productSizeRequestParamsDTO.getProductIds();
        System.console().printf("productSizes: %s", productIds);
        List<ProductSize> productSizes = productSizeRepository.findAllByProductId(productIds);

        if (productSizes.isEmpty()) {
            throw new EntityNotFoundException("Product size not found");
        }
        List<SizeProduct> sizeProduct = sizeProductRepository.findByProductId(productIds);
        System.console().printf("sizeProduct: %s", sizeProduct);

        return productSizes.stream().map(productSize -> {
            ProductSizeResponseDTO dto = new ProductSizeResponseDTO();
            dto.toDto(productSize);

            return dto;
        }).collect(Collectors.toList());
    }
}
