package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipmentproduct.ShipmentProductCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.sizeproduct.SizeProductShipmentRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductSupplierResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.shipment.ShipmentResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.shipmentproduct.ShipmentProductResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.shipmentproduct.SizeProductShipmentResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProductId;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProductId;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.*;
import tdc.edu.vn.project_mobile_be.interfaces.service.ShipmentService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        shipment.getShipmentProducts().clear();
        shipment.getShipmentProducts().addAll(shipmentProducts);
        return savedShipment;
    }


    @Override
    public Shipment updateShipment(ShipmentUpdateRequestDTO requestDTO, UUID shipmentId) {
        LocalDateTime shipmentDateTime = resolveShipmentDate(requestDTO.getShipmentDate());
        validateDiscountAndCost(requestDTO.getShipmentDiscount(), requestDTO.getShipmentShipCost());
        if (shipmentId == null) {
            throw new IllegalArgumentException("Shipment ID cannot be null");
        }
        Shipment shipment = shipmentRepository
                .findById(shipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found"));
        validateDiscountAndCost(requestDTO.getShipmentDiscount(), requestDTO.getShipmentShipCost());

        ProductSupplier productSupplier = productSupplierRepository
                .findById(requestDTO.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        shipment.setShipmentDate(Timestamp.valueOf(shipmentDateTime));
        shipment.setProductSupplier(productSupplier);
        shipment.setShipmentShipCost(requestDTO.getShipmentShipCost());
        shipment.setShipmentDiscount(requestDTO.getShipmentDiscount());

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
        Shipment shipment = shipmentRepository.findShipmentById(shipmentId);
            ShipmentResponseDTO shipmentResponseDTO = new ShipmentResponseDTO();
            shipmentResponseDTO.toDto(shipment);
            shipmentResponseDTO.setShipmentId(shipment.getShipmentId());
            shipmentResponseDTO.setShipmentShipCost(shipment.getShipmentShipCost());
            shipmentResponseDTO.setShipmentDate(shipment.getShipmentDate().toLocalDateTime().toLocalDate());
        shipmentResponseDTO.setShipmentDiscount(shipment.getShipmentDiscount());

        setShipmentResponseDTO(shipment, shipmentResponseDTO);
        return shipmentResponseDTO;
    }

    private void setShipmentResponseDTO(Shipment shipment, ShipmentResponseDTO shipmentResponseDTO) {
        ProductSupplierResponseDTO productSupplierResponseDTO = new ProductSupplierResponseDTO();
        productSupplierResponseDTO.toDto(shipment.getProductSupplier());
        shipmentResponseDTO.setProductSupplierResponseDTO(productSupplierResponseDTO);

        List<ShipmentProductResponseDTO> shipmentProductResponseDTOs = new ArrayList<>();

        List<ShipmentProduct> shipmentProducts = shipmentProductRepository.findByShipmentId(shipment.getShipmentId());

        Map<UUID, Map<Double, List<ShipmentProduct>>> groupedProducts = shipmentProducts.stream().collect(Collectors.groupingBy(sp -> sp.getProduct().getProductId(), Collectors.groupingBy(ShipmentProduct::getPrice)));

        groupedProducts.forEach((productId, priceMap) -> {
            priceMap.forEach((price, products) -> {
                List<SizeProductShipmentResponseDTO> sizeShipmentProductResponseDTOs = new ArrayList<>();

                products.forEach(shipmentProduct -> {
                    SizeProductShipmentResponseDTO sizeProductShipmentResponseDTO = new SizeProductShipmentResponseDTO();
                    sizeProductShipmentResponseDTO.setSizeId(shipmentProduct.getProductSize().getProductSizeId());
                    sizeProductShipmentResponseDTO.setProductSizeQuantity(shipmentProduct.getQuantity());
                    sizeShipmentProductResponseDTOs.add(sizeProductShipmentResponseDTO);
                });

                ShipmentProductResponseDTO shipmentProductResponseDTO = new ShipmentProductResponseDTO();
                shipmentProductResponseDTO.setProductId(productId);
                shipmentProductResponseDTO.setShipmentProductPrice(price);
                shipmentProductResponseDTO.setSizesProduct(sizeShipmentProductResponseDTOs);

                shipmentProductResponseDTOs.add(shipmentProductResponseDTO);
            });
        });
        shipmentResponseDTO.setResponseDTOS(shipmentProductResponseDTOs);
    }

    @Override
    public List<ShipmentResponseDTO> getAllShipment() {
        List<ShipmentResponseDTO> shipmentResponseDTOList = new ArrayList<>();

        // Lấy tất cả các shipment từ cơ sở dữ liệu
        shipmentRepository.findAll().forEach(shipment -> {
            ShipmentResponseDTO shipmentResponseDTO = new ShipmentResponseDTO();
            shipmentResponseDTO.toDto(shipment);
            shipmentResponseDTO.setShipmentId(shipment.getShipmentId());
            shipmentResponseDTO.setShipmentShipCost(shipment.getShipmentShipCost());
            shipmentResponseDTO.setShipmentDate(shipment.getShipmentDate().toLocalDateTime().toLocalDate());

            // Thiết lập ProductSupplierResponseDTO cho ShipmentResponseDTO
            setShipmentResponseDTO(shipment, shipmentResponseDTO);

            // Thêm ShipmentResponseDTO vào danh sách chính
            shipmentResponseDTOList.add(shipmentResponseDTO);
        });

        return shipmentResponseDTOList;
    }

    public void validateDiscountAndCost(float discount, double shipCost) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        if (shipCost < 0) {
            throw new IllegalArgumentException("Shipping cost cannot be negative");
        }
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
            for (SizeProductShipmentRequestParamsDTO paramSize : param.getSizesProduct()) {
                Product product = productRepository
                        .findById(param.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Product with ID " + param.getProductId() + " not found"));
                ProductSize productSize = productSizeRepository
                        .findById(paramSize.getSizeId())
                        .orElseThrow(() -> new IllegalArgumentException("Size with ID " + paramSize.getSizeId() + " not found"));
                product.setProductPrice(calculateProductPrice(param.getProductPrice()));
                int quantity = calculateQuantityProduct(product.getProductQuantity(), paramSize.getProductSizeQuantity());
                product.setProductQuantity(quantity);

                SizeProductId id = new SizeProductId();
                id.setProduct_id(product.getProductId());
                id.setProduct_size_id(productSize.getProductSizeId());
                SizeProduct sizeProduct = new SizeProduct();
                sizeProduct.setSize(productSize);
                sizeProduct.setProduct(product);
                sizeProduct.setId(id);

                log.info("Size product: {}", sizeProduct);

                if (!product.getSizeProducts().contains(sizeProduct)) {
                    sizeProductRepository.save(sizeProduct);
                }

                product.getSizeProducts().forEach(sizeProductMod -> {
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
            for (SizeProductShipmentRequestParamsDTO paramSize : param.getSizesProduct()) {
                Product product = productRepository
                        .findById(param.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Product with ID " + param.getProductId() + " not found"));
                ShipmentProductId shipmentProductId = new ShipmentProductId();
                shipmentProductId.setProduct_id(param.getProductId());
                shipmentProductId.setShipment_id(shipment.getShipmentId());
                shipmentProductId.setProduct_size_id(paramSize.getSizeId());


                ShipmentProduct shipmentProduct = shipmentProductRepository.findById(shipmentProductId).orElse(shipmentProductRepository.save(new ShipmentProduct(shipmentProductId, product, shipment, productSizeRepository.findById(paramSize.getSizeId()).get(), param.getProductPrice(), paramSize.getProductSizeQuantity())));

                product.getShipmentProducts().remove(shipmentProduct);

                product.setProductPrice(calculateProductPrice(param.getProductPrice()));
                int quantity = calculateQuantityProduct(product.getProductQuantity(), paramSize.getProductSizeQuantity());
                product.setProductQuantity(quantity);


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