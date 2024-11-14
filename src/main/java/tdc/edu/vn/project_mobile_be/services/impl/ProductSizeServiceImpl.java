package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ParamNullException;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductSizeRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productsize.ProductSizeCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productsize.ProductSizeUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductSizeResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductSizeRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.SizeProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductSizeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public ProductSize createProductSize(ProductSizeCreateRequestDTO request) {
        if(request == null){
            throw new ParamNullException("Request is null");
        }
        ProductSize productSize = request.toEntity();
        productSize.setProductSizeId(UUID.randomUUID());
        return productSizeRepository.save(productSize);
    }

    @Override
    public ProductSize updateProductSize(ProductSizeUpdateRequestDTO request,UUID productSizeId) {
        Optional<ProductSize> productSizeOP = productSizeRepository.findById(productSizeId);
        if (productSizeOP.isEmpty()) {
            throw new EntityNotFoundException("Product size not found");
        }
        ProductSize productSize = productSizeOP.get();
        productSize.setProductSizeName(request.getProductSizeName());
        return productSizeRepository.save(productSize);
    }

    @Override
    public boolean deleteProductSize(UUID productSizeId) {
        Optional<ProductSize> productSizeOP = productSizeRepository.findById(productSizeId);
        if (productSizeOP.isEmpty()) {
            throw new EntityNotFoundException("Product size not found");
        }
        ProductSize productSize = productSizeOP.get();
        productSizeRepository.delete(productSize);
        return true;
    }


    @Override
    public List<ProductSizeResponseDTO> getAllProductSizeByProductId(ProductSizeRequestParamsDTO productSizeRequestParamsDTO) {
        List<UUID> productIds = productSizeRequestParamsDTO.getProductIds();

        List<ProductSize> productSizes = productSizeRepository.findAllByProductId(productIds);

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
        List<ProductSizeResponseDTO> result = new ArrayList<>();
        if (categoryId != null) {
            List<ProductSize> productSizes = productSizeRepository.findProductSizesByCategory(categoryId);
            System.console().printf("productSizes: " + productSizes);
            if (productSizes.isEmpty()) {
                throw new EntityNotFoundException("Product Supplier not found");
            }

            productSizes.stream().toList().forEach(productSize -> {
                ProductSizeResponseDTO productSizeResponseDTO = new ProductSizeResponseDTO();
                productSizeResponseDTO.toDto(productSize);
                result.add(productSizeResponseDTO);
            });
            if (result.isEmpty()) {
                throw new ListNotFoundException("List product size not found");
            }
        }
        if(categoryId == null || categoryId.equals("")){
            List<ProductSize> productSizes = productSizeRepository.findAll();
            if (productSizes.isEmpty()) {
                throw new ListNotFoundException("List product size không tồn tại");
            }
            productSizes.stream().toList().forEach(productSize -> {
                ProductSizeResponseDTO productSizeResponseDTO = new ProductSizeResponseDTO();
                productSizeResponseDTO.toDto(productSize);
                result.add(productSizeResponseDTO);
            });
        }

        return result;
    }

    @Override
    public Page<ProductSizeResponseDTO> getAllProductSize(Pageable pageable) {
        Page<ProductSize> productSizes = productSizeRepository.findAll(pageable);
        if (productSizes.isEmpty()) {
            throw new ListNotFoundException("List product size không tồn tại");
        }
        return productSizes.map(productSize -> {
            ProductSizeResponseDTO dto = new ProductSizeResponseDTO();
            dto.toDto(productSize);
            return dto;
        });
    }

}
