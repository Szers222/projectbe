package tdc.edu.vn.project_mobile_be.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.ProductSpecifications;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.NumberErrorException;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductSizeResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductSupplierResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CategoryRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductSizeRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductSupplierRepository;
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
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        return products.map(product -> {
            Product productWithImages = productRepository.findByIdWithImages(product.getProductId())
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
            Product productWithImages = productRepository.findByIdWithImages(product.getProductId())
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
            Product productWithImages = productRepository.findByIdWithImages(product.getProductId())
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
                .map(ProductSize::getProductSizeId)  // Lấy danh sách UUID của Size hợp lệ
                .collect(Collectors.toList());

        if (existingSizeIds.size() != sizeIds.size()) {
            throw new EntityNotFoundException("One or more sizes not found");
        }

        return productRepository.findBySizes(existingSizeIds, pageable).map(product -> {
            Product productWithImages = productRepository.findByIdWithImages(product.getProductId())
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
            Product productWithImages = productRepository.findByIdWithImages(product.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(productWithImages);  // Mapping product with images to the DTO
            return dto;
        });
    }

    @Override
    public Page<ProductResponseDTO> findProductsByFilters(ProductRequestParamsDTO params, Pageable pageable) {
        Specification<Product> spec = Specification.where(null);  // Khởi tạo Specification rỗng

        // Lọc theo danh mục (category)
        if (params.getCategoryId() != null && !categoryRepository.findById(params.getCategoryId()).isEmpty()) {
            spec = spec.and(ProductSpecifications.hasCategory(params.getCategoryId()));
        }

        // Lọc theo khoảng giá
        if (params.getMinPrice() != null && params.getMaxPrice() != null) {
            if (params.getMinPrice().compareTo(params.getMaxPrice()) > 0) {
                throw new NumberErrorException("Min price must be less than max price");
            }
            if (params.getMinPrice().compareTo(BigDecimal.ZERO) < 0 || params.getMaxPrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new NumberErrorException("Min price and max price must be greater than 0");
            }
            if (params.getMinPrice().compareTo(BigDecimal.ZERO) == 0 && params.getMaxPrice().compareTo(BigDecimal.ZERO) == 0) {
                throw new NumberErrorException("Min price and max price must not be equal to 0");
            }
            if (params.getMaxPrice().compareTo(BigDecimal.valueOf(2000000)) > 0) {
                throw new NumberErrorException("Max price must not be greater than 2 million");
            }
            spec = spec.and(ProductSpecifications.priceBetween(params.getMinPrice(), params.getMaxPrice()));
        }

        // Lọc theo kích thước
        if (params.getSizeIds() != null && !params.getSizeIds().isEmpty()) {
            spec = spec.and(ProductSpecifications.hasSizes(params.getSizeIds()));
        }

        // Lọc theo nhà cung cấp (supplier)
        if (params.getSupplierId() != null && !productSupplierRepository.findById(params.getSupplierId()).isEmpty()) {
            spec = spec.and(ProductSpecifications.hasSupplier(params.getSupplierId()));
        }


        // Truy vấn với các tiêu chí kết hợp
        Page<Product> products = productRepository.findAll(spec, pageable);


        return products.map(product -> {
            Product productWithImages = productRepository.findByIdWithImages(product.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(productWithImages);  // Mapping product with images to the DTO
            return dto;
        });
    }

    @Override
    public List<ProductSupplier> findProductSuppliersByCategory(ProductRequestParamsDTO params) {
        if (categoryRepository.findById(params.getCategoryId()).isEmpty()) {
            throw new EntityNotFoundException("Category not found");
        }
        List<ProductSupplier> productSuppliers = productRepository.findProductSuplierByCategory(params.getCategoryId());
        ProductSupplierResponseDTO productSupplierResponseDTO = new ProductSupplierResponseDTO();
        return productSuppliers.stream().map(productSupplier -> {
            productSupplierResponseDTO.toDto(productSupplier);
            return productSupplier;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductSize> findProductSizesByCategory(ProductRequestParamsDTO params) {
        if (categoryRepository.findById(params.getCategoryId()).isEmpty()) {
            throw new EntityNotFoundException("Category not found");
        }
        List<ProductSize> productSizes = productRepository.findProductSizesByCategory(params.getCategoryId());

        ProductSizeResponseDTO productSupplierResponseDTO = new ProductSizeResponseDTO();
        return productSizes.stream().map(productSupplier -> {
            productSupplierResponseDTO.toDto(productSupplier);
            return productSupplier;
        }).collect(Collectors.toList());
    }


}
