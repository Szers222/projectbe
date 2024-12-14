package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.ParamNullException;
import tdc.edu.vn.project_mobile_be.dtos.requests.newsale.NewSaleCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.newsale.NewSaleUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.newsale.NewSaleResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.collection.NewSale;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.NewSaleRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.NewSaleService;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductService;

import java.util.*;

@Service
public class NewSaleServiceImpl extends AbService<NewSale, UUID> implements NewSaleService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private NewSaleRepository newSaleRepository;

    @Override
    public NewSale createSlideshowImage(NewSaleCreateRequestDTO params) {
        if (params == null) {
            return null;
        }
        NewSale newSale = params.toEntity();
        newSale.setNewSaleId(UUID.randomUUID());
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

        newSale.setNewSaleStatus(params.getStatus());
        newSale.setNewSaleIndex(params.getImageIndex());
        newSale.setNewSaleAlt(params.getImageAlt());
        newSale.setProducts(products);

        return newSaleRepository.save(newSale);
    }

    @Override
    public NewSaleResponseDTO getNewSale(int status) {
        NewSale newSale = newSaleRepository.findByStatus(status);
        if (newSale == null) {
            return null;
        }
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        List<Product> listProduct = productRepository.findByNewSalesStatus(newSale.getNewSaleId());
        listProduct.forEach(product -> {
            ProductResponseDTO productResponseDTO = productService.getProductById(product.getProductId());
            productResponseDTOS.add(productResponseDTO);
        });

        NewSaleResponseDTO newSaleResponseDTO = new NewSaleResponseDTO();
        newSaleResponseDTO.toDto(newSale);
        newSaleResponseDTO.setNewSaleId(newSale.getNewSaleId());
        newSaleResponseDTO.setImageAlt(newSale.getNewSaleAlt());
        newSaleResponseDTO.setImageIndex(newSale.getNewSaleStatus());
        newSaleResponseDTO.setProducts(productResponseDTOS);

        return newSaleResponseDTO;
    }

    @Override
    public void deleteNewSale(UUID id) {
        if (id == null) {
            throw new ParamNullException("id không được null");
        }
        NewSale newSale = newSaleRepository.findById(id).orElse(null);
        if (newSale == null) {
            throw new RuntimeException("NewSale không tồn tại");
        }

        newSaleRepository.delete(newSale);
    }

    @Override
    public List<NewSaleResponseDTO> getAllNewSale() {
        List<NewSale> newSales = newSaleRepository.findAll();
        List<NewSaleResponseDTO> newSaleResponseDTOS = new ArrayList<>();
        newSales.forEach(newSale -> {
            NewSaleResponseDTO newSaleResponseDTO = new NewSaleResponseDTO();
            newSaleResponseDTO.toDto(newSale);
            newSaleResponseDTO.setNewSaleId(newSale.getNewSaleId());
            newSaleResponseDTO.setImageAlt(newSale.getNewSaleAlt());
            newSaleResponseDTO.setImageIndex(newSale.getNewSaleIndex());
            newSaleResponseDTO.setStatus(newSale.getNewSaleStatus());
            newSaleResponseDTOS.add(newSaleResponseDTO);
        });
        return newSaleResponseDTOS;
    }

    @Override
    public NewSale updateNewSale(UUID id, NewSaleUpdateRequestDTO params) {
        if (id == null) {
            throw new ParamNullException("id không được null");
        }
        NewSale newSale = newSaleRepository.findById(id).orElse(null);
        if (newSale == null) {
            throw new RuntimeException("NewSale không tồn tại");
        }
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

        newSale.setNewSaleStatus(params.getStatus());
        newSale.setNewSaleIndex(params.getImageIndex());
        newSale.setNewSaleAlt(params.getImageAlt());
        newSale.setProducts(products);

        return newSaleRepository.save(newSale);
    }

}
