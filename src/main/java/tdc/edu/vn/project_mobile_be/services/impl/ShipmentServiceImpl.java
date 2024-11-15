package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipmentproduct.ShipmentProductCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.shipment.ShipmentResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.*;
import tdc.edu.vn.project_mobile_be.interfaces.service.ShipmentService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class ShipmentServiceImpl extends AbService<Shipment, UUID> implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSupplierRepository productSupplierRepository;
    @Autowired
    private ShipmentProductRepository shipmentProductRepository;
    @Autowired
    private SizeProductRepository sizeProductRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Override
    public Shipment createShipment(ShipmentCreateRequestDTO requestDTO) {
        LocalDateTime shipmentDateTime = resolveShipmentDate(requestDTO.getShipmentDate());
        validateDiscountAndCost(requestDTO.getShipmentDiscount(), requestDTO.getShipmentShipCost());

        ProductSupplier productSupplier = productSupplierRepository
                .findById(requestDTO.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));
        log.info("Product supplier: {}", productSupplier);
        Shipment shipment = requestDTO.toEntity();
        shipment.setShipmentId(UUID.randomUUID());
        shipment.setShipmentDate(Timestamp.valueOf(shipmentDateTime));
        shipment.setProductSupplier(productSupplier);
        shipment.setShipmentShipCost(requestDTO.getShipmentShipCost());
        Shipment savedShipment = shipmentRepository.save(shipment);

        Set<ShipmentProduct> shipmentProducts = createShipmentProduct(requestDTO.getShipmentProductCreateRequestDTO(), savedShipment);
        if (shipmentProducts.isEmpty()) {
            throw new IllegalArgumentException("Shipment product is empty");
        }
        log.info("Shipment products: {}", shipmentProducts);
        shipment.getShipmentProducts().addAll(shipmentProducts);
        return savedShipment;
    }

    @Override
    public Shipment updateShipment(ShipmentUpdateRequestDTO requestDTO, UUID shipmentId) {
        if (shipmentId == null) {
            throw new IllegalArgumentException("Shipment ID cannot be null");
        }

        Shipment shipment = shipmentRepository
                .findById(shipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found"));

        LocalDateTime shipmentDateTime = resolveShipmentDate(requestDTO.getShipmentDate());
        validateDiscountAndCost(requestDTO.getShipmentDiscount(), requestDTO.getShipmentShipCost());

        ProductSupplier productSupplier = productSupplierRepository
                .findById(requestDTO.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        shipment.setShipmentDate(Timestamp.valueOf(shipmentDateTime));
        shipment.setProductSupplier(productSupplier);

        Set<ShipmentProduct> shipmentProducts = createShipmentProduct(requestDTO.getShipmentProductCreateRequestDTO(), shipment);
        if (shipmentProducts.isEmpty()) {
            throw new IllegalArgumentException("Shipment product is empty");
        }
        shipment.setShipmentProducts(shipmentProducts);

        return shipmentRepository.save(shipment);
    }

    @Override
    public boolean deleteShipment(UUID shipmentId) {
        Shipment shipment = shipmentRepository
                .findById(shipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found"));

        shipmentRepository.delete(shipment);
        return true;
    }

    @Override
    public ShipmentResponseDTO getShipmentById(UUID shipmentId) {
        Shipment shipment = shipmentRepository
                .findById(shipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found"));

        ShipmentResponseDTO shipmentResponseDTO = new ShipmentResponseDTO();
        shipmentResponseDTO.toDto(shipment);
        return shipmentResponseDTO;
    }

    @Override
    public ShipmentResponseDTO getShipmentBySupplier(String supplier) {
        // Method body to be implemented
        return null;
    }

    @Override
    public Page<ShipmentResponseDTO> getAllShipment(Pageable pageable) {
        // Method body to be implemented
        return null;
    }

    public boolean validateDiscountAndCost(float discount, double shipCost) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        if (shipCost < 0) {
            throw new IllegalArgumentException("Shipping cost cannot be negative");
        }
        return true;
    }

    public <T extends ShipmentProductCreateRequestDTO> Set<ShipmentProduct> createShipmentProduct(List<T> requestDTO, Shipment shipment) {
        if (requestDTO == null || requestDTO.isEmpty()) {
            throw new IllegalArgumentException("Request data cannot be null or empty.");
        }
        if (shipment == null) {
            throw new IllegalArgumentException("Shipment cannot be null.");
        }

        Set<ShipmentProduct> shipmentProducts = new HashSet<>();
        for (T params : requestDTO) {
            ShipmentProduct shipmentProduct = params.toEntity();

            Product product = productRepository
                    .findById(params.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product with ID " + params.getProductId() + " not found"));

            product.setProductPrice(calculateProductPrice(params.getProductPrice()));
            int quantity = calculateQuantityProduct(product.getProductQuantity(), params.getProductQuantity());
            product.setProductQuantity(quantity);
            if (product.getSizeProducts() == null || product.getSizeProducts().isEmpty()) {
                ProductSize newSize = productSizeRepository.findByProductSizeId(params.getSizeProductId());
                SizeProduct newSizeProduct = new SizeProduct();
                newSizeProduct.setProduct(product);
                newSizeProduct.setSize(newSize);
                newSizeProduct.setQuantity(params.getProductQuantity());
                sizeProductRepository.save(newSizeProduct);
            } else {
                product.getSizeProducts().forEach(sizeProduct -> {
                    if (sizeProduct.getSize().getProductSizeId().equals(params.getSizeProductId())) {
                        sizeProduct.setQuantity(sizeProduct.getQuantity() + params.getProductQuantity());
                    }
                });
            }


            productRepository.save(product);

            shipmentProduct.setPrice(params.getProductPrice());
            shipmentProduct.setQuantity(params.getProductQuantity());
            shipmentProduct.setProduct(product);
            shipmentProduct.setShipment(shipment);
            shipmentProductRepository.save(shipmentProduct);
            shipmentProducts.add(shipmentProduct);
        }

        return shipmentProducts;
    }

    public double calculateProductPrice(double productPrice) {
        final double MARKUP_PERCENTAGE = 10.0;
        return productPrice * (1 + MARKUP_PERCENTAGE / 100);
    }

    private LocalDateTime resolveShipmentDate(LocalDate shipmentDate) {
        return shipmentDate != null ? shipmentDate.atStartOfDay() : LocalDateTime.now();
    }

    private int calculateQuantityProduct(int quantity, int productQuantity) {
        return quantity + productQuantity;
    }
}