package tdc.edu.vn.project_mobile_be.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.dtos.requests.CartRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.CartResponseDTO;
import tdc.edu.vn.project_mobile_be.interfaces.service.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(@RequestBody CartRequestDTO cartRequestDTO) {
        return ResponseEntity.ok(cartService.addToCart(cartRequestDTO));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponseDTO> getCartByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @DeleteMapping("/{cartId}/product/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable UUID cartId, @PathVariable UUID productId) {
        cartService.removeProductFromCart(cartId, productId);
        return ResponseEntity.noContent().build();
    }
}


