package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipmentproduct.ShipmentProductCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.sizeproduct.SizeProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductSupplierResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.shipment.ShipmentResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.shipmentproduct.ShipmentProductResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProductId;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.*;
import tdc.edu.vn.project_mobile_be.interfaces.service.ShipmentService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

        Set<ShipmentProduct> shipmentProducts = updateShipmentProduct(requestDTO.getShipmentProductCreateRequestDTO(), shipment);
        if (shipmentProducts.isEmpty()) {
            throw new IllegalArgumentException("Shipment product is empty");
        }
        shipment.getShipmentProducts().clear();
        shipment.getShipmentProducts().addAll(shipmentProducts);
        return shipmentRepository.save(shipment);
    }

    @Override
    public boolean deleteShipment(UUID shipmentId) {
        Shipment shipment = shipmentRepository
                .findById(shipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found"));

        shipment.getShipmentProducts().clear();
        shipmentRepository.save(shipment);
        shipmentRepository.delete(shipment);
        return true;
    }


    @Override
    public ShipmentResponseDTO getShipmentBySupplier(String supplier) {
        // Method body to be implemented
        return null;
    }


    @Override
    public ShipmentResponseDTO getShipmentById(UUID shipmentId) {
        Shipment shipment = shipmentRepository
                .findById(shipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found"));

        List<ShipmentProduct> shipmentProducts = shipmentProductRepository.findByShipmentId(shipment.getShipmentId());
        List<ShipmentProductResponseDTO> shipmentProductResponseDTOs = new ArrayList<>();
        shipmentProducts.forEach(shipmentProduct -> {
            List<UUID> sizeProductIds = shipmentProduct.getProduct().getSizeProducts().stream().map(sizeProduct -> sizeProduct.getSize().getProductSizeId()).toList();
            ShipmentProductResponseDTO shipmentProductResponseDTO = new ShipmentProductResponseDTO();
            shipmentProductResponseDTO.setShipmentProductPrice(shipmentProduct.getPrice());
            shipmentProductResponseDTO.setShipmentProductQuantity(shipmentProduct.getQuantity());
            shipmentProductResponseDTO.setShipmentProductId(shipmentProduct.getProduct().getProductId());
            shipmentProductResponseDTO.setShipmentProductSizeId(sizeProductIds);
            shipmentProductResponseDTOs.add(shipmentProductResponseDTO);
        });
        ShipmentResponseDTO shipmentResponseDTO = new ShipmentResponseDTO();
        shipmentResponseDTO.toDto(shipment);
        shipmentResponseDTO.setShipmentId(shipment.getShipmentId());
        shipmentResponseDTO.setShipmentShipCost(shipment.getShipmentShipCost());
        shipmentResponseDTO.setShipmentDate(shipment.getShipmentDate().toLocalDateTime().toLocalDate());
        ProductSupplierResponseDTO productSupplierResponseDTO = new ProductSupplierResponseDTO();
        productSupplierResponseDTO.toDto(shipment.getProductSupplier());
        shipmentResponseDTO.setProductSupplierResponseDTO(productSupplierResponseDTO);
        shipmentResponseDTO.setResponseDTOS(shipmentProductResponseDTOs);
        return shipmentResponseDTO;
    }

    @Override
    public Page<ShipmentResponseDTO> getAllShipment(Pageable pageable) {
        Page<ShipmentResponseDTO> shipmentResponseDTOs = shipmentRepository.findAll(pageable).map(shipment -> {
            List<ShipmentProduct> shipmentProducts = shipmentProductRepository.findByShipmentId(shipment.getShipmentId());
            List<ShipmentProductResponseDTO> shipmentProductResponseDTOs = new ArrayList<>();
            shipmentProducts.forEach(shipmentProduct -> {
                List<UUID> sizeProductIds = shipmentProduct.getProduct().getSizeProducts().stream().map(sizeProduct -> sizeProduct.getSize().getProductSizeId()).toList();
                ShipmentProductResponseDTO shipmentProductResponseDTO = new ShipmentProductResponseDTO();
                shipmentProductResponseDTO.setShipmentProductPrice(shipmentProduct.getPrice());
                shipmentProductResponseDTO.setShipmentProductQuantity(shipmentProduct.getQuantity());
                shipmentProductResponseDTO.setShipmentProductId(shipmentProduct.getProduct().getProductId());
                shipmentProductResponseDTO.setShipmentProductSizeId(sizeProductIds);
                shipmentProductResponseDTOs.add(shipmentProductResponseDTO);
            });

            ShipmentResponseDTO shipmentResponseDTO = new ShipmentResponseDTO();
            shipmentResponseDTO.toDto(shipment);
            shipmentResponseDTO.setShipmentId(shipment.getShipmentId());
            shipmentResponseDTO.setShipmentShipCost(shipment.getShipmentShipCost());
            shipmentResponseDTO.setShipmentDate(shipment.getShipmentDate().toLocalDateTime().toLocalDate());
            ProductSupplierResponseDTO productSupplierResponseDTO = new ProductSupplierResponseDTO();
            productSupplierResponseDTO.toDto(shipment.getProductSupplier());
            shipmentResponseDTO.setProductSupplierResponseDTO(productSupplierResponseDTO);
            shipmentResponseDTO.setResponseDTOS(shipmentProductResponseDTOs);
            return shipmentResponseDTO;
        });
        return shipmentResponseDTOs;
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
        if (requestDTO == null) {
            throw new IllegalArgumentException("Request data cannot be null or empty.");
        }
        if (shipment == null) {
            throw new IllegalArgumentException("Shipment cannot be null");
        }

        Set<ShipmentProduct> shipmentProducts = new HashSet<>();


        for (ShipmentProductCreateRequestDTO param : requestDTO) {
            for (SizeProductRequestParamsDTO paramSize : param.getSizesProduct()) {
                Product product = productRepository
                        .findById(paramSize.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Product with ID " + paramSize.getProductId() + " not found"));
                ProductSize productSize = productSizeRepository
                        .findById(paramSize.getSizeId())
                        .orElseThrow(() -> new IllegalArgumentException("Size with ID " + paramSize.getSizeId() + " not found"));
                product.setProductPrice(calculateProductPrice(param.getProductPrice()));
                int quantity = calculateQuantityProduct(product.getProductQuantity(), paramSize.getProductSizeQuantity());
                product.setProductQuantity(quantity);
                if (product.getSizeProducts() == null || product.getSizeProducts().isEmpty()) {
                    List<ProductSize> newSizes = productSizeRepository.findByProductId(paramSize.getSizeId());
                    SizeProduct newSizeProduct = new SizeProduct();
                    newSizeProduct.setProduct(product);
                    newSizes.forEach(size -> {
                        if (size.getProductSizeId().equals(paramSize.getSizeId())) {
                            newSizeProduct.setSize(size);
                        }
                    });
                    newSizeProduct.setQuantity(paramSize.getProductSizeQuantity());
                    sizeProductRepository.save(newSizeProduct);
                } else {
                    product.getSizeProducts().forEach(sizeProduct -> {
                        if (sizeProduct.getSize().getProductSizeId().equals(paramSize.getSizeId())) {
                            sizeProduct.setQuantity(sizeProduct.getQuantity() + paramSize.getProductSizeQuantity());
                        }
                    });

                }

                product.getSizeProducts().forEach(sizeProduct -> {
                    int productQuantity = sizeProductRepository.findByProductId(product.getProductId()).stream().mapToInt(SizeProduct::getQuantity).sum();
                    product.setProductQuantity(productQuantity);
                });

                productRepository.save(product);
                ShipmentProduct shipmentProduct = new ShipmentProduct();
                shipmentProduct.setPrice(param.getProductPrice());
                shipmentProduct.setProductSize(productSize);
                shipmentProduct.setQuantity(paramSize.getProductSizeQuantity());
                shipmentProduct.setProduct(product);
                shipmentProduct.setShipment(shipment);
                shipmentProductRepository.save(shipmentProduct);
                shipmentProducts.add(shipmentProduct);
            }
        }
        return shipmentProducts;
    }

    public <T extends ShipmentProductCreateRequestDTO> Set<ShipmentProduct> updateShipmentProduct(List<T> requestDTO, Shipment shipment) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("Request data cannot be null or empty.");
        }
        if (shipment == null) {
            throw new IllegalArgumentException("Shipment cannot be null");
        }

        Set<ShipmentProduct> shipmentProducts = new HashSet<>();

        for (ShipmentProductCreateRequestDTO param : requestDTO) {
            for (SizeProductRequestParamsDTO paramSize : param.getSizesProduct()) {
                Product product = productRepository
                        .findById(paramSize.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Product with ID " + paramSize.getProductId() + " not found"));
                ShipmentProductId shipmentProductId = new ShipmentProductId();
                shipmentProductId.setProduct_id(paramSize.getProductId());
                shipmentProductId.setShipment_id(shipment.getShipmentId());
                shipmentProductId.setProduct_size_id(paramSize.getSizeId());

                ShipmentProduct shipmentProduct = shipmentProductRepository
                        .findById(shipmentProductId)
                        .orElseThrow(() -> new IllegalArgumentException("Shipment product not found"));

                product.setProductPrice(calculateProductPrice(param.getProductPrice()));
                int quantity = calculateQuantityProduct(product.getProductQuantity(), paramSize.getProductSizeQuantity());
                product.setProductQuantity(quantity);
                if (product.getSizeProducts() == null || product.getSizeProducts().isEmpty()) {
                    List<ProductSize> newSizes = productSizeRepository.findByProductId(paramSize.getSizeId());
                    SizeProduct newSizeProduct = new SizeProduct();
                    newSizeProduct.setProduct(product);
                    newSizes.forEach(size -> {
                        if (size.getProductSizeId().equals(paramSize.getSizeId())) {
                            newSizeProduct.setSize(size);
                        }
                    });
                    newSizeProduct.setQuantity(paramSize.getProductSizeQuantity());
                    sizeProductRepository.save(newSizeProduct);
                } else {
                    product.getSizeProducts().forEach(sizeProduct -> {
                        if (sizeProduct.getSize().getProductSizeId().equals(paramSize.getSizeId())) {
                            int quantityProduct = shipmentProductRepository.findByProductIdAndProductSizeId(product.getProductId(), paramSize.getSizeId()).stream().mapToInt(ShipmentProduct::getQuantity).sum();
                            sizeProduct.setQuantity(quantityProduct);
                        }
                    });
                }
                product.getSizeProducts().forEach(sizeProduct -> {
                    int productQuantity = sizeProductRepository.findByProductId(product.getProductId()).stream().mapToInt(SizeProduct::getQuantity).sum();
                    product.setProductQuantity(productQuantity);
                });
                productRepository.save(product);
                shipmentProduct.setPrice(param.getProductPrice());
                shipmentProduct.setQuantity(paramSize.getProductSizeQuantity());
                shipmentProductRepository.save(shipmentProduct);
                shipmentProducts.add(shipmentProduct);
            }
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