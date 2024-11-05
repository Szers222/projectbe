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
import tdc.edu.vn.project_mobile_be.dtos.responses.coupon.CouponResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.coupon.Coupon;
import tdc.edu.vn.project_mobile_be.interfaces.service.CouponService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @PostMapping({"/coupon", "/coupon/"})
    public ResponseEntity<ResponseData<?>> createCoupon(
            @Valid @RequestBody CouponCreateRequestDTO params,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        Coupon createdCoupon = couponService.createCoupon(params);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Coupon tạo thành công !", createdCoupon);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @GetMapping({"/coupons", "/coupons/"})
    public ResponseEntity<ResponseData<PagedModel<EntityModel<CouponResponseDTO>>>> getCoupons(
            @RequestParam CouponRequestParamsDTO params,
            PagedResourcesAssembler<CouponResponseDTO> assembler) {
        {

            Sort.Direction sortDirection = params.getDirection().
                    equalsIgnoreCase("asc") ?
                    Sort.Direction.ASC :
                    Sort.Direction.DESC;
            Sort sortBy = Sort.by(sortDirection, params.getSort());

            Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sortBy);
            Page<CouponResponseDTO> data = couponService.getCoupons(1, pageable);
            PagedModel<EntityModel<CouponResponseDTO>> pagedModel = assembler.toModel(data);
            ResponseData<PagedModel<EntityModel<CouponResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);
            return ResponseEntity.ok(responseData);
        }
    }
}
