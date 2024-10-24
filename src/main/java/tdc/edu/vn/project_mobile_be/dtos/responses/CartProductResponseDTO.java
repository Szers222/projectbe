package tdc.edu.vn.project_mobile_be.dtos.responses;

import lombok.Data;

import java.util.UUID;

@Data
public class CartProductResponseDTO {
    private UUID productId;
    private String productName;
    private int quantity;
    private double price;
}
