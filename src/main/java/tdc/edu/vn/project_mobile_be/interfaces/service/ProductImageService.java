package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductImageResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface ProductImageService extends IService<ProductImage, UUID> {
    List<ProductImageResponseDTO> findAllByProductId(UUID productId);

//    ProductImage createProductImage(ProductImageCreateRequestDTO createProductImageRequestDTO, MultipartFile file);

//    List<ProductImage> createProductImage(ProductImageUpdateRequestDTO createProductImageRequestDTO);

//    boolean deleteProductImage(UUID id);
}
