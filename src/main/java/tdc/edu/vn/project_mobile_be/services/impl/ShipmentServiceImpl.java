package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipmentproduct.ShipmentProductCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.shipment.ShipmentResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductSupplierRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ShipmentRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ShipmentService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ShipmentServiceImpl extends AbService<Shipment, UUID> implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSupplierRepository productSupplierRepository;

    @Override
    public Shipment createShipment(ShipmentCreateRequestDTO requestDTO) {
        LocalDateTime shipmentDateTime = resolveShipmentDate(requestDTO.getShipmentDate());
        validateDiscountAndCost(requestDTO.getShipmentDiscount(), requestDTO.getShipmentShipCost());

        ProductSupplier productSupplier = productSupplierRepository
                .findById(requestDTO.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        Shipment shipment = requestDTO.toEntity();
        shipment.setShipmentId(UUID.randomUUID());
        shipment.setShipmentDate(Timestamp.valueOf(shipmentDateTime));
        shipment.setProductSupplier(productSupplier);

        Shipment savedShipment = shipmentRepository.save(shipment);

        Set<ShipmentProduct> shipmentProducts = createShipmentProduct(requestDTO.getShipmentProductCreateRequestDTO(), savedShipment);
        if (shipmentProducts.isEmpty()) {
            throw new IllegalArgumentException("Shipment product is empty");
        }
        shipment.setShipmentProducts(shipmentProducts);
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
        for (T shipmentProductCreateRequestDTO : requestDTO) {
            ShipmentProduct shipmentProduct = shipmentProductCreateRequestDTO.toEntity();

            Product product = productRepository
                    .findById(shipmentProductCreateRequestDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product with ID " + shipmentProductCreateRequestDTO.getProductId() + " not found"));

            product.setProductQuantity(product.getProductQuantity() + shipmentProductCreateRequestDTO.getProductQuantity());
            product.setProductPrice(calculateProductPrice(shipmentProductCreateRequestDTO.getProductPrice()));
            productRepository.save(product);

            shipmentProduct.setProduct(product);
            shipmentProduct.setShipment(shipment);
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
}
