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
import tdc.edu.vn.project_mobile_be.dtos.requests.contentslide.ContentSlideCreateDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.contentslide.ContentSlideUpdateDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.contentslide.ContentSlideResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.contentslide.Content;
import tdc.edu.vn.project_mobile_be.interfaces.service.ContentSlideService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ContentSlideController {

    @Autowired
    private ContentSlideService contentSlideService;

    @PostMapping("/contentslide")
    public ResponseEntity<ResponseData<?>> createContentSlide(
            @RequestBody @Valid ContentSlideCreateDTO params,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }

        Content contentSlide = contentSlideService.createContentSlide(params);

        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Tạo content slide thành công", contentSlide);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/contentslide/{id}")
    public ResponseEntity<ResponseData<?>> updateContentSlide(
            @PathVariable UUID id,
            @RequestBody @Valid ContentSlideUpdateDTO params,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }

        Content contentSlide = contentSlideService.updateContentSlide(params, id);

        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Cập nhật content slide thành công", contentSlide);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/contentslide/{id}")
    public ResponseEntity<ResponseData<?>> deleteContentSlide(@PathVariable UUID id) {
        contentSlideService.deleteContentSlide(id);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Xóa content slide thành công", null);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/contentslides")
    public ResponseEntity<ResponseData<?>> getContentSlides() {
        List<ContentSlideResponseDTO> contentSlides = contentSlideService.getAllContentSlides();
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Lấy danh sách content slides thành công", contentSlides);
        return ResponseEntity.ok(responseData);
    }
}
