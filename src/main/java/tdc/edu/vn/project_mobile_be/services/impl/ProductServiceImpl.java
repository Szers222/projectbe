package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.ProductSpecifications;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.InvalidLinkException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.NumberErrorException;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateProductRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.ProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CategoryRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductSupplierRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductService;

import java.math.BigDecimal;
import java.util.UUID;

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
    private ProductSupplierRepository productSupplierRepository;

    @Override
    public Product createProduct(CreateProductRequestDTO params) {
        return null;
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
        if (params.getDirection() != null && params.getSort() != null) {
            spec = spec.and(ProductSpecifications.hasSort(params.getSort(), params.getDirection()));
        }
        if (params.getSearch() == null || params.getSearch().isEmpty() || params.getSearch().isBlank()) {

        }
        if (params.getSearch() == null || params.getSearch().isEmpty() || params.getSearch().isBlank()) {
            throw new InvalidLinkException("Đường dẫn không hợp lệ");
        } else {
            spec = spec.and(ProductSpecifications.hasSearch(params.getSearch()));
        }


        // Truy vấn với các tiêu chí kết hợp
        Page<Product> products = productRepository.findAll(spec, pageable);
        if (products.isEmpty() || products.getSize() == 0) {
            throw new ListNotFoundException("Không tìm thấy sản phẩm");
        }


        return products.map(product -> {
            Product productWithImages = productRepository.findByIdWithImages(product.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            ProductResponseDTO dto = new ProductResponseDTO();
            dto.toDto(productWithImages);  // Mapping product with images to the DTO
            return dto;
        });
    }

}
