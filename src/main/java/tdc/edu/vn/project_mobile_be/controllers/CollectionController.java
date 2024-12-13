package tdc.edu.vn.project_mobile_be.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.MultipleFieldsNullOrEmptyException;
import tdc.edu.vn.project_mobile_be.dtos.requests.collection.CollectionCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.collection.CollectionResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.collection.Collection;
import tdc.edu.vn.project_mobile_be.interfaces.service.CollectionService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    private final ObjectMapper objectMapper;

    @Autowired
    public CollectionController(@Lazy ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/collection", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<?>> createShipment(
            @RequestPart @Valid String paramsJson,
            @RequestPart MultipartFile file,
            BindingResult bindingResult
    ) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        CollectionCreateRequestDTO params = objectMapper.readValue(paramsJson, CollectionCreateRequestDTO.class);

        Collection collection = collectionService.createSlideshowImage(params, file);

        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Cập nhật sản phẩm thành công", collection);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/collection/{id}")
    public ResponseEntity<ResponseData<?>> getCollection(@PathVariable UUID id) {
        CollectionResponseDTO collection = collectionService.getCollection(id);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Lấy thông tin sản phẩm thành công", collection);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/collection/{id}")
    public ResponseEntity<ResponseData<?>> deleteCollection(@PathVariable UUID id) {
        collectionService.deleteCollection(id);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Xóa sản phẩm thành công", null);
        return ResponseEntity.ok(responseData);
    }
}
