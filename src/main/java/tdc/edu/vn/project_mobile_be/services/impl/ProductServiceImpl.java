package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.NumberErrorException;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.*;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl extends AbService<Product, UUID> implements ProductService {

    public final int PRODUCT_CLOTHES = 0;
    public final int PRODUCT_SHOES = 1;
    public final int PRODUCT_ACCESSORIES = 2;


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductSizeRepository sizeRepository;
    @Autowired
    private ProductSupplierRepository productSupplierRepository;



    @Override
    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        return products.map(product -> {
            Product productWithImages = productRepository.findByIdWithImages(product.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(productWithImages);  // Mapping product with images to the DTO
            return dto;
        });
    }

    @Override
    public Page<ProductResponseDTO> findAllByCategory(UUID categoryId, Pageable pageable) {

        if (categoryRepository.findById(categoryId).isEmpty()) {
            throw new EntityNotFoundException("Category not found");
        }
        return productRepository.findAllByCategory(categoryId, pageable).map(product -> {
            Product productWithImages = productRepository.findByIdWithImages(product.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(productWithImages);  // Mapping product with images to the DTO
            return dto;
        });
    }

    @Override
    public Page<ProductResponseDTO> findProductsPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        if (minPrice.compareTo(maxPrice) > 0) {
            throw new NumberErrorException("Giá trị minPrice phải nhỏ hơn maxPrice");
        }
        if (minPrice.compareTo(BigDecimal.ZERO) < 0 || maxPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new NumberErrorException("Giá trị minPrice và maxPrice phải lớn hơn 0");
        }
        if (minPrice.compareTo(BigDecimal.ZERO) == 0 && maxPrice.compareTo(BigDecimal.ZERO) == 0) {
            throw new NumberErrorException("Giá trị minPrice và maxPrice không được cùng bằng 0");
        }
        if (maxPrice.compareTo(BigDecimal.valueOf(2000000)) > 0) {
            throw new NumberErrorException("Giá trị maxPrice không được lớn hơn 2 triệu");
        }
        return productRepository.findProductsPriceRange(minPrice, maxPrice, pageable).map(product -> {
            Product productWithImages = productRepository.findByIdWithImages(product.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(productWithImages);  // Mapping product with images to the DTO
            return dto;
        });
    }

    @Override
    public Page<ProductResponseDTO> findBySizes(List<UUID> sizeIds, Pageable pageable) {

        if (sizeIds.isEmpty()) {
            throw new ListNotFoundException("List size is empty");
        }
        List<UUID> existingSizeIds = sizeRepository.findAllById(sizeIds).stream()
                .map(ProductSize::getId)  // Lấy danh sách UUID của Size hợp lệ
                .collect(Collectors.toList());

        if (existingSizeIds.size() != sizeIds.size()) {
            throw new EntityNotFoundException("One or more sizes not found");
        }

        return productRepository.findBySizes(existingSizeIds, pageable).map(product -> {
            Product productWithImages = productRepository.findByIdWithImages(product.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(productWithImages);  // Mapping product with images to the DTO
            return dto;
        });
    }

    @Override
    public Page<ProductResponseDTO> findByIdSuplier(UUID suplierId, Pageable pageable) {
        if (productSupplierRepository.findById(suplierId).isEmpty()) {
            throw new EntityNotFoundException("Supplier not found");
        }
        return productRepository.findByIdSuplier(suplierId, pageable).map(product -> {
            Product productWithImages = productRepository.findByIdWithImages(product.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(productWithImages);  // Mapping product with images to the DTO
            return dto;
        });

    }


}
