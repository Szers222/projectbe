package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.FileEmptyException;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductImageResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductImageRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl extends AbService<ProductImage, UUID> implements ProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductImageResponseDTO> findAllByProductId(UUID productId) {
        if (productRepository.findById(productId) == null) {
            throw new EntityNotFoundException("Product not found");
        }
        List<ProductImage> productImages = productImageRepository.findByProductImageId(productId);
        return productImages.stream().map(productImage -> {
            ProductImageResponseDTO dto = new ProductImageResponseDTO();
            dto.toDto(productImage);  // Giới hạn độ sâu nếu cần
            return dto;
        }).collect(Collectors.toList());
    }

//    @Override
//    public List<ProductImage> createProductImage(ProductImageCreateRequestDTO createProductImageRequestDTO, MultipartFile file) {
//        if (createProductImageRequestDTO.getProductId() != null) {
//            Optional<Product> productOptional = productRepository.findById(createProductImageRequestDTO.getProductId());
//            if (productOptional.isEmpty()) {
//                throw new EntityNotFoundException("Product not found");
//
//            }
////            if (file.isEmpty()) {
////                throw new FileEmptyException("File is empty");
////            }
//            try {
////                String imagePath = saveImage(file);
//                Product product = productOptional.get();
//                ProductImage productImage = createProductImageRequestDTO.toEntity();
//                productImage.setProductImageId(UUID.randomUUID());
//                productImage.setProduct(product);
//                return productImageRepository.save(productImage);
//            } catch (IOException e) {
//                throw new EntityNotFoundException("Error when save image");
//            }
//        }
//        throw new EntityNotFoundException("Id product is null");
//    }



//    private String saveImage(MultipartFile file) throws IOException {
//        String uploadDir = "src/main/resources/static/images";
//        Path uploadPath = Paths.get(uploadDir);
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//        String fileName = file.getOriginalFilename();
//        Path filePath = uploadPath.resolve(fileName);
//        Files.copy(file.getInputStream(), filePath);
//        return fileName;
//    }
}
