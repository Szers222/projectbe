package tdc.edu.vn.project_mobile_be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tdc.edu.vn.project_mobile_be.DTO.slideshowDTO.CreateSlideshowImageDTO;
import tdc.edu.vn.project_mobile_be.DTO.slideshowDTO.GetSlideshowImageDTO;
import tdc.edu.vn.project_mobile_be.DTO.slideshowDTO.UpdateSlideshowImageDTO;
import tdc.edu.vn.project_mobile_be.entities.slideshows.SlideshowImage;
import tdc.edu.vn.project_mobile_be.services.SlideshowImageService;

import java.util.List;

@RestController
@RequestMapping("/api/slideshow-images")
public class SlideshowImageController {

    @Autowired
    private SlideshowImageService service;

    // Lấy tất cả hình ảnh
    @GetMapping
    public List<GetSlideshowImageDTO> getAllImages() {
        return service.getAll().stream()
                .map(GetSlideshowImageDTO::fromEntity)
                .toList();
    }

    // Lấy hình ảnh theo ID
    @GetMapping("/{id}")
    public ResponseEntity<GetSlideshowImageDTO> getImageById(@PathVariable String id) {
        SlideshowImage image = service.getById(id);
        return image != null ? ResponseEntity.ok(GetSlideshowImageDTO.fromEntity(image)) : ResponseEntity.notFound().build();
    }

    // Thêm mới hình ảnh
    @PostMapping("/add")
    public ResponseEntity<GetSlideshowImageDTO> createImage(@RequestBody CreateSlideshowImageDTO imageDTO) {
        SlideshowImage newImage = imageDTO.toEntity();
        SlideshowImage createdImage = service.create(newImage);
        return ResponseEntity.ok(GetSlideshowImageDTO.fromEntity(createdImage));
    }

    // Cập nhật hình ảnh
    @PutMapping("/{id}")
    public ResponseEntity<GetSlideshowImageDTO> updateImage(@PathVariable String id, @RequestBody UpdateSlideshowImageDTO imageDTO) {
        SlideshowImage updatedImage = imageDTO.toEntity();
        SlideshowImage savedImage = service.update(id, updatedImage);
        return savedImage != null ? ResponseEntity.ok(GetSlideshowImageDTO.fromEntity(savedImage)) : ResponseEntity.notFound().build();
    }

    // Xóa hình ảnh
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
