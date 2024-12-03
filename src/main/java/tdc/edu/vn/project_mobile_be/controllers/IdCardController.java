package tdc.edu.vn.project_mobile_be.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.idcard.CreateIdCardRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import tdc.edu.vn.project_mobile_be.interfaces.service.IdCardService;

import java.util.UUID;


@RequestMapping("/api/v1")
@RestController
public class IdCardController {

    @Autowired
    private IdCardService idCardService;
    private final ObjectMapper objectMapper;

    @Autowired
    public IdCardController(@Lazy ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @PostMapping(value = "/auth/idcard", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<IdCard>> uploadIdCardImages(
            @ModelAttribute @Valid CreateIdCardRequestDTO idCardRequestDTO) {
        // Gọi service để upload ảnh và tạo mới IdCard
        IdCard createdIdCard = idCardService.uploadIdCardImages(idCardRequestDTO);
        return ResponseEntity.ok(
                new ResponseData<>(HttpStatus.CREATED
                        , "IdCard đã được tạo mới thành công"
                        , createdIdCard)
        );
    }
    @PutMapping(value = "/auth/idcard/{cardId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<IdCard>> updateIdCard(
            @PathVariable UUID cardId, @ModelAttribute @Valid CreateIdCardRequestDTO params) {

        IdCard updatedIdCard = idCardService.updateIdCard(cardId, params);
        return ResponseEntity.ok(
                new ResponseData<>(HttpStatus.OK, "IdCard đã được cập nhật thành công", updatedIdCard)
        );
    }
    @DeleteMapping(value = "/auth/idcard/{cardId}")
    public ResponseEntity<ResponseData<String>> deleteIdCard(@PathVariable UUID cardId) {
        idCardService.deleteIdCard(cardId);
        return ResponseEntity.ok(
                new ResponseData<>(HttpStatus.OK, "IdCard đã được xóa thành công", null)
        );
    }


}


