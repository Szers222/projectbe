package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.FileEmptyException;
import tdc.edu.vn.project_mobile_be.commond.customexception.FileUploadException;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageCreateWithProductRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.productimage.ProductImageUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductImageResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductImageRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductImageService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl extends AbService<ProductImage, UUID> implements ProductImageService {


    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;

    @Override
    @Transactional
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

    @Override
    @Transactional
    public ProductImage createProductImage(ProductImageCreateRequestDTO params, MultipartFile file) {
        if (params.getProductId() != null) {
            Optional<Product> productOptional = productRepository.findById(params.getProductId());
            if (productOptional.isEmpty()) {
                throw new EntityNotFoundException("Product not found");

            }
            if (file.isEmpty()) {
                throw new FileEmptyException("File is empty");
            }
            try {
                String imageUrl = googleCloudStorageService.uploadFile(file);
                Product product = productOptional.get();
                ProductImage productImage = params.toEntity();
                productImage.setProductImageId(UUID.randomUUID());
                productImage.setProduct(product);
                productImage.setProductImagePath(imageUrl);

                return productImageRepository.save(productImage);
            } catch (IOException e) {
                throw new EntityNotFoundException("Error when save image");
            }
        }
        throw new EntityNotFoundException("Id product is null");
    }

    @Override
    @Transactional
    public Set<ProductImage> createProductImageWithProduct(
            ProductImageCreateWithProductRequestDTO params,
            UUID productId,
            MultipartFile[] files) {

        // Kiểm tra ID sản phẩm có null không
        if (productId == null) {
            throw new EntityNotFoundException("Product ID is null");
        }

        // Lấy sản phẩm và kiểm tra tồn tại
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Kiểm tra có file nào không
        if (files.length == 0) {
            throw new FileEmptyException("No files provided");
        }

        Set<ProductImage> result = new HashSet<>();
        try {
            // Duyệt qua từng file để upload
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    throw new FileEmptyException("One of the files is empty");
                }
                // Upload file và lấy URL từ Google Cloud Storage
                String imageUrl = googleCloudStorageService.uploadFile(file);

                // Tạo ProductImage mới
                ProductImage productImage = params.toEntity();
                productImage.setProductImageId(UUID.randomUUID());
                productImage.setProduct(product);
                productImage.setProductImagePath(imageUrl);

                // Lưu ProductImage vào database
                ProductImage savedProductImage = productImageRepository.save(productImage);
                result.add(savedProductImage);
            }
        } catch (IOException e) {
            // Thông báo lỗi khi upload file thất bại
            throw new FileUploadException("Xãy ra lỗi khi lưu file vào Google Cloud Storage" + e);
        }

        return result;
    }


    @Override
    public ProductImage updateProductImage(ProductImageUpdateRequestDTO params, MultipartFile file, UUID productImageId) {
        Optional<ProductImage> productImageOp = productImageRepository.findById(productImageId);
        if (productImageOp.isEmpty()) {
            throw new EntityNotFoundException("Product image not found");
        }
        if (file.isEmpty()) {
            throw new FileEmptyException("File is empty");
        }

        if (params.getProductId() != null) {
            Optional<Product> productOptional = productRepository.findById(params.getProductId());
            if (productOptional.isEmpty()) {
                throw new EntityNotFoundException("Product not found");

            }
            try {
                ProductImage productImage = productImageOp.get();
                String imageUrl = googleCloudStorageService.uploadFile(file);
                Product product = productOptional.get();
                productImage.setProductImageId(UUID.randomUUID());
                productImage.setProduct(product);
                productImage.setProductImagePath(imageUrl);

                return productImageRepository.save(productImage);
            } catch (IOException e) {
                throw new EntityNotFoundException("Error when save image");
            }
        }
        throw new EntityNotFoundException("Id product is null");
    }

    @Override
    public boolean deleteProductImage(UUID id) {
        return false;
    }


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
