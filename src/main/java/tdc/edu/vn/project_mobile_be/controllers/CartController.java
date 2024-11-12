package tdc.edu.vn.project_mobile_be.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.MultipleFieldsNullOrEmptyException;
import tdc.edu.vn.project_mobile_be.dtos.requests.cart.CartCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CartRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.CartService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping(value = {"/cart", "/cart/"})
    public ResponseEntity<ResponseData<?>> createCart(
            @Valid @RequestBody CartCreateRequestDTO params,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        Cart cartCreated = cartService.createCartNoUser(params);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Cart tạo thành công !", cartCreated);
        return ResponseEntity.ok(responseData);
    }
    @GetMapping(value = {"/carts", "/cart/"})
    public ResponseEntity<ResponseData<?>> getAllCart() {
        List<Cart> carts = cartRepository.findAll();
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Danh sách Cart", carts);
        return ResponseEntity.ok(responseData);
    }
}
