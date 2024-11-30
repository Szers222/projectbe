package tdc.edu.vn.project_mobile_be.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.MultipleFieldsNullOrEmptyException;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.CartCreateRequestByUserDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.CartCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.CartUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.RemoveSizeProductRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.cart.CartResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.interfaces.service.CartService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {
    @Autowired
    private CartService cartService;


    @PostMapping(value = {"/cart/create_guest/{guestId}", "/cart/create_guest/{guestId}/"})
    public ResponseEntity<ResponseData<?>> createCart(
            @Valid @RequestBody CartCreateRequestDTO params,
            @PathVariable UUID guestId,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        Cart cartCreated = cartService.createCartNoUser(params, guestId);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Cart tạo thành công !", cartCreated);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping(value = {"/cart/user", "/cart/user/"})
    public ResponseEntity<ResponseData<?>> createCartByUser(
            @Valid @RequestBody CartCreateRequestByUserDTO params,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        Cart cartCreated = cartService.createCartByUser(params.getUserId());
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Cart tạo thành công !", cartCreated);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping(value = {"/cart/{cartId}", "/cart/{cartId}/"})
    public ResponseEntity<ResponseData<?>> addCart(
            @Valid @RequestBody CartUpdateRequestDTO params,
            @PathVariable UUID cartId,
            BindingResult bindingResult
    ) {
        System.console().printf("Cart ID: %s", cartId);
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        Cart cartCreated = cartService.addProductToCart(params, cartId);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Thêm sản phẩm thành công !", cartCreated);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping(value = {"/cart/{cartId}", "/cart/{cartId}/"})
    public ResponseEntity<ResponseData<?>> deleteProductInCart(
            @Valid @RequestBody RemoveSizeProductRequestParamsDTO params,
            @PathVariable UUID cartId,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        cartService.deleteProductInCart(params, cartId);
        ResponseData<?> responseData = getResponseData(params);
        return ResponseEntity.ok(responseData);
    }

    private static ResponseData<?> getResponseData(RemoveSizeProductRequestParamsDTO params) {
        ResponseData<?> responseData;
        if (params.getSizeProduct().getProductSizeQuantity() != 0) {
            responseData = new ResponseData<>(HttpStatus.OK, "Số lượng sản phẩm đã giảm " + params.getSizeProduct().getProductSizeQuantity(), null);
        } else if (params.getSizeProduct().getProductSizeQuantity() == 0) {
            responseData = new ResponseData<>(HttpStatus.OK, "Xóa sản phẩm thành công !", null);
        } else {
            responseData = new ResponseData<>(HttpStatus.BAD_REQUEST, "Sản phẩm không tồn tại trong giỏ hàng !", null);
        }
        return responseData;
    }

    @GetMapping(value = {"/carts/{cartId}", "/cart/{cartId}/"})
    public ResponseEntity<ResponseData<?>> getAllCartByCart(
            @PathVariable UUID cartId
    ) {
        CartResponseDTO cart = cartService.findCartByIdCart(cartId);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Danh sách Cart", cart);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = {"/carts/guest/{guestId}", "/cart/guest/{guestId}/"})
    public ResponseEntity<ResponseData<?>> getAllCartByGuest(
            @PathVariable UUID guestId
    ) {
        CartResponseDTO cart = cartService.findCartByIdGuest(guestId);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Danh sách Cart", cart);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = {"/carts/user/{userId}", "/cart/user/{userId}/"})
    public ResponseEntity<ResponseData<?>> getCartByUser(
            @PathVariable UUID userId
    ) {
        CartResponseDTO cart = cartService.findCartByIdUser(userId);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Danh sách Cart", cart);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = {"/carts/wishlist/user/{userId}", "/cart/user/{userId}/"})
    public ResponseEntity<ResponseData<?>> getCartWishlistByUser(
            @PathVariable UUID userId
    ) {
        CartResponseDTO cart = cartService.findCartWishlistByIdUser(userId);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Danh sách Cart", cart);
        return ResponseEntity.ok(responseData);
    }


}
