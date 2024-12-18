package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageParamsWithProductRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageUpdateWithProductRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductImageResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductImageService extends IService<ProductImage, UUID> {
    List<ProductImageResponseDTO> findAllByProductId(UUID productId);

    List<ProductImage> createProductImage(ProductImageCreateRequestDTO params, MultipartFile[] file);

    Set<ProductImage> createProductImageWithProduct(ProductImageParamsWithProductRequestDTO params, UUID productId, MultipartFile[] files);

    ProductImage updateProductImage(ProductImageUpdateRequestDTO params, MultipartFile file, UUID productImageId);

    Set<ProductImage> updateProductImageForProduct(ProductImageParamsWithProductRequestDTO params, UUID productId, MultipartFile[] files);

    boolean deleteProductImage(UUID productImageId);

    boolean deleteProductImageByProductId(UUID productId);
}
