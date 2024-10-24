package tdc.edu.vn.project_mobile_be.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.CartRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.CartResponseDTO;
import tdc.edu.vn.project_mobile_be.interfaces.service.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    @Autowired
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ResponseData<CartResponseDTO>> addToCart(@RequestBody CartRequestDTO cartRequestDTO) {
        CartResponseDTO cartResponseDTO = cartService.addToCart(cartRequestDTO);
        ResponseData<CartResponseDTO> responseData = new ResponseData<>(HttpStatus.CREATED, "Thêm sản phẩm vào giỏ hàng thành công!", cartResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseData<CartResponseDTO>> getCartByUserId(@PathVariable UUID userId) {
        CartResponseDTO cartResponseDTO = cartService.getCartByUserId(userId);
        ResponseData<CartResponseDTO> responseData = new ResponseData<>(HttpStatus.OK, "Lấy giỏ hàng thành công!", cartResponseDTO);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{cartId}/product/{productId}")
    public ResponseEntity<ResponseData<Void>> removeProductFromCart(@PathVariable UUID cartId, @PathVariable UUID productId) {
        cartService.removeProductFromCart(cartId, productId);
        ResponseData<Void> responseData = new ResponseData<>(HttpStatus.NO_CONTENT, "Xóa sản phẩm thành công!", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
    }
    @PostMapping("/confirm/{cartId}")
    public ResponseEntity<Void> confirmCart(@PathVariable UUID cartId) {
        cartService.confirmCart(cartId);
        return ResponseEntity.ok().build();
    }
}



