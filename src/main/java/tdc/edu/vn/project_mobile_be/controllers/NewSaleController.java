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
import tdc.edu.vn.project_mobile_be.dtos.requests.newsale.NewSaleCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.newsale.NewSaleUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.newsale.NewSaleResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.collection.NewSale;
import tdc.edu.vn.project_mobile_be.interfaces.service.NewSaleService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class NewSaleController {
    @Autowired
    private NewSaleService newsaleService;

    private final ObjectMapper objectMapper;

    @Autowired
    public NewSaleController(@Lazy ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/newsale", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<?>> createShipment(@RequestPart @Valid String paramsJson, @RequestPart MultipartFile file, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }
        NewSaleCreateRequestDTO params = objectMapper.readValue(paramsJson, NewSaleCreateRequestDTO.class);

        NewSale newsale = newsaleService.createSlideshowImage(params);

        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Cập nhật sản phẩm thành công", newsale);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/newsale")
    public ResponseEntity<ResponseData<?>> getNewSale(@RequestParam int status) {
        NewSaleResponseDTO newsale = newsaleService.getNewSale(status);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Lấy thông tin sản phẩm thành công", newsale);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/newsale/{id}")
    public ResponseEntity<ResponseData<?>> deleteNewSale(@PathVariable UUID id) {
        newsaleService.deleteNewSale(id);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Xóa sản phẩm thành công", null);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/newsales")
    public ResponseEntity<ResponseData<?>> getAllNewSale() {
        List<NewSaleResponseDTO> newsales = newsaleService.getAllNewSale();
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Lấy thông tin sản phẩm thành công", newsales);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/newsale/{id}")
    public ResponseEntity<ResponseData<?>> updateNewSale(@PathVariable UUID id, @RequestBody NewSaleUpdateRequestDTO params) {
        NewSale newsale = newsaleService.updateNewSale(id, params);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Cập nhật sản phẩm thành công", newsale);
        return ResponseEntity.ok(responseData);
    }
}
