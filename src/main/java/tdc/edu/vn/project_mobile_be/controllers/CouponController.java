package tdc.edu.vn.project_mobile_be.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.MultipleFieldsNullOrEmptyException;
import tdc.edu.vn.project_mobile_be.dtos.requests.coupon.CouponCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.coupon.CouponRequestParamsDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.coupon.CouponUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.coupon.CouponResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.interfaces.service.CouponService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CouponController {

    @Autowired
    private CouponService couponService;

    // Endpoint to create a new coupon
    @PostMapping({"/coupon", "/coupon/"})
    public ResponseEntity<ResponseData<?>> createCoupon(
            @Valid @RequestBody CouponCreateRequestDTO params,
            BindingResult bindingResult) {
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        // Create the coupon
        Coupon createdCoupon = couponService.createCoupon(params);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Coupon tạo thành công !", createdCoupon);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    // Endpoint to get a paginated list of coupons
    @GetMapping({"/coupons", "/coupons/"})
    public ResponseEntity<ResponseData<PagedModel<EntityModel<CouponResponseDTO>>>> getCoupons(
            @ModelAttribute CouponRequestParamsDTO params,
            PagedResourcesAssembler<CouponResponseDTO> assembler) {
        // Determine sort direction
        Sort.Direction sortDirection = params.getDirection().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(sortDirection, params.getSort());

        // Create pageable object
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sortBy);
        Page<CouponResponseDTO> data = couponService.getCoupons(1, pageable);
        PagedModel<EntityModel<CouponResponseDTO>> pagedModel = assembler.toModel(data);
        ResponseData<PagedModel<EntityModel<CouponResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);
        return ResponseEntity.ok(responseData);
    }

    // Endpoint to get coupons by type
    @GetMapping({"/coupons/type/{type}", "/coupons/type/{type}/"})
    public ResponseEntity<ResponseData<List<CouponResponseDTO>>> getCouponByType(@PathVariable int type) {
        List<CouponResponseDTO> data = couponService.getCouponByType(type);
        ResponseData<List<CouponResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success", data);
        return ResponseEntity.ok(responseData);
    }

    // Endpoint to delete a coupon by ID
    @DeleteMapping({"/coupon/{couponId}", "/coupon/{couponId}/"})
    public ResponseEntity<ResponseData<?>> deleteCoupon(@PathVariable UUID couponId) {
        boolean isDeleted = couponService.deleteCoupon(couponId);
        if (isDeleted) {
            ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Xóa thành công !", null);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.BAD_REQUEST, "Xóa thất bại !", null);
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }

    // Endpoint to update a coupon by ID
    @PutMapping({"/coupon/{couponId}", "/coupon/{couponId}/"})
    public ResponseEntity<ResponseData<?>> updateCoupon(
            @Valid @RequestBody CouponUpdateRequestDTO params,
            @PathVariable UUID couponId,
            BindingResult bindingResult) {
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        // Update the coupon
        Coupon updatedCoupon = couponService.updateCoupon(params, couponId);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Coupon cập nhật thành công !", updatedCoupon);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @GetMapping({"/coupon/{couponId}", "/coupon/{couponId}/"})
    public ResponseEntity<ResponseData<CouponResponseDTO>> getCouponById(@PathVariable UUID couponId) {
        CouponResponseDTO data = couponService.getCouponById(couponId);
        ResponseData<CouponResponseDTO> responseData = new ResponseData<>(HttpStatus.OK, "Success", data);
        return ResponseEntity.ok(responseData);
    }
}