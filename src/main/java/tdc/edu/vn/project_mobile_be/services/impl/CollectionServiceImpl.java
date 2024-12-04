package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.commond.customexception.ParamNullException;
import tdc.edu.vn.project_mobile_be.dtos.requests.collection.CollectionCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.collection.CollectionResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.collection.Collection;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CollectionRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.CollectionService;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductService;

import java.util.*;

@Service
public class CollectionServiceImpl extends AbService<Collection, UUID> implements CollectionService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private ProductService productService;

    @Override
    public Collection createSlideshowImage(CollectionCreateRequestDTO params, MultipartFile file) {
        if (params == null) {
            return null;
        }
        Collection collection = params.toEntity();
        collection.setCollectionId(UUID.randomUUID());
        Set<Product> products = new HashSet<>();
        for (UUID productId : params.getProducts()) {
            productRepository.findById(productId).ifPresent(products::add);
        }
        if (products.size() > 6) {
            throw new RuntimeException("products không được lớn hơn 6");
        }
        if (products.isEmpty()) {
            throw new RuntimeException("products không được nhỏ hơn 1");
        }
        try {
            String url = googleCloudStorageService.uploadFile(file);
            collection.setCollectionUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        collection.setCollectionIndex(params.getImageIndex());
        collection.setCollectionAlt(params.getImageAlt());
        collection.setProducts(products);

        return collectionRepository.save(collection);
    }

    @Override
    public CollectionResponseDTO getCollection(UUID id) {
        Collection collection = collectionRepository.findById(id).orElse(null);
        if (collection == null) {
            return null;
        }


        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        List<Product> listProduct = productRepository.findByCollectionId(id);
        listProduct.forEach(product -> {
            ProductResponseDTO productResponseDTO = productService.getProductById(product.getProductId());
            productResponseDTOS.add(productResponseDTO);
        });

        CollectionResponseDTO collectionResponseDTO = new CollectionResponseDTO();
        collectionResponseDTO.toDto(collection);
        collectionResponseDTO.setCollectionId(collection.getCollectionId());
        collectionResponseDTO.setImageAlt(collection.getCollectionAlt());
        collectionResponseDTO.setImageUrl(collection.getCollectionUrl());
        collectionResponseDTO.setImageIndex(collection.getCollectionIndex());
        collectionResponseDTO.setProducts(productResponseDTOS);

        return collectionResponseDTO;
    }

    @Override
    public void deleteCollection(UUID id) {
        if (id == null) {
            throw new ParamNullException("id không được null");
        }
        Collection collection = collectionRepository.findById(id).orElse(null);
        if (collection == null) {
            throw new RuntimeException("Collection không tồn tại");
        }

        collectionRepository.delete(collection);
    }

}
