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
import tdc.edu.vn.project_mobile_be.dtos.requests.slideshowimage.SlideshowImageCreateDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.slideshowimage.SlideshowImageUpdateDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.slideshow.SlideshowResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.slideshow.SlideshowImage;
import tdc.edu.vn.project_mobile_be.interfaces.service.SlideshowImageService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class SlideshowController {
    @Autowired
    private SlideshowImageService slideshowImageService;

    private final ObjectMapper objectMapper;

    @Autowired
    public SlideshowController(@Lazy ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/slideshow", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
        SlideshowImageCreateDTO params = objectMapper.readValue(paramsJson, SlideshowImageCreateDTO.class);
        params.setImagePath(file);

        SlideshowImage slideshowImage = slideshowImageService.createSlideshowImage(params);

        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Tạo banner thành công", slideshowImage);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping(value = "/slideshow/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<?>> updateShipment(
            @PathVariable UUID id,
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
        SlideshowImageUpdateDTO params = objectMapper.readValue(paramsJson, SlideshowImageUpdateDTO.class);
        params.setImagePath(file);

        SlideshowImage slideshowImage = slideshowImageService.updateSlideshowImage(params, id);

        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Cập nhật banner thành công", slideshowImage);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/slideshow/{id}")
    public ResponseEntity<ResponseData<?>> deleteShipment(@PathVariable("id") UUID id) {
        slideshowImageService.deleteSlideshowImage(id);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Xóa banner thành công", null);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/slideshows")
    public ResponseEntity<ResponseData<?>> getSlideshows(@RequestParam String content) {
        List<SlideshowResponseDTO> slideshowImages = slideshowImageService.getSlideshowImage(content);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Lấy danh sách banner thành công", slideshowImages);
        return ResponseEntity.ok(responseData);
    }

}
