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
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.shipment.ShipmentUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.shipment.ShipmentResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;
import tdc.edu.vn.project_mobile_be.interfaces.service.ShipmentService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @PostMapping("/shipment")
    public ResponseEntity<ResponseData<?>> createShipment(
            @Valid @RequestBody ShipmentCreateRequestDTO params,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        Shipment shipment = shipmentService.createShipment(params);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Tạo Lô Hàng Thành Công", shipment);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    @PutMapping("/shipment/{shipmentId}")
    public ResponseEntity<ResponseData<?>> updateShipment(
            @Valid @RequestBody ShipmentUpdateRequestDTO params,
            @PathVariable UUID shipmentId,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        Shipment shipment = shipmentService.updateShipment(params, shipmentId);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Cập Nhật Lô Hàng Thành Công", shipment);
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @DeleteMapping("/shipment/{shipmentId}")
    public ResponseEntity<ResponseData<?>> deleteShipment(@PathVariable UUID shipmentId) {
        boolean result = shipmentService.deleteShipment(shipmentId);
        if (result) {
            ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Xóa Lô Hàng Thành Công", null);
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        } else {
            ResponseData<?> responseData = new ResponseData<>(HttpStatus.NOT_FOUND, "Không Tìm Thấy Lô Hàng", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
    }

    @GetMapping({"/shipments", "/shipments"})
    public ResponseEntity<ResponseData<PagedModel<EntityModel<ShipmentResponseDTO>>>> getShipments(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            PagedResourcesAssembler<ShipmentResponseDTO> assembler
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("shipmentDate").descending());
        Page<ShipmentResponseDTO> shipments = shipmentService.getAllShipment(pageable);
        if (shipments.isEmpty()) {
            ResponseData<PagedModel<EntityModel<ShipmentResponseDTO>>> responseData = new ResponseData<>(HttpStatus.NOT_FOUND, "Không Tìm Thấy Lô Hàng", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        PagedModel<EntityModel<ShipmentResponseDTO>> pagedModel = assembler.toModel(shipments);
        ResponseData<PagedModel<EntityModel<ShipmentResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/shipment/{shipmentId}")
    public ResponseEntity<ResponseData<ShipmentResponseDTO>> getShipmentById(@PathVariable UUID shipmentId) {
        ShipmentResponseDTO shipment = shipmentService.getShipmentById(shipmentId);
        ResponseData<ShipmentResponseDTO> responseData = new ResponseData<>(HttpStatus.OK, "Success", shipment);
        return ResponseEntity.ok(responseData);
    }



}
