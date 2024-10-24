package tdc.edu.vn.project_mobile_be.dtos.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class CartRequestDTO {
    private UUID userId;
    private UUID productId;
    private int quantity;
}

