package tdc.edu.vn.project_mobile_be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.DTO.SlideshowImageDTO;
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
    public List<SlideshowImageDTO> getAllImages() {
        return service.getAll().stream()
                .map(SlideshowImageDTO::fromEntity)
                .toList();
    }

    // Lấy hình ảnh theo ID
    @GetMapping("/{id}")
    public ResponseEntity<SlideshowImageDTO> getImageById(@PathVariable String id) {
        SlideshowImage image = service.getById(id);
        return image != null ? ResponseEntity.ok(SlideshowImageDTO.fromEntity(image)) : ResponseEntity.notFound().build();
    }

    // Thêm mới hình ảnh
    @PostMapping("/add")
    public ResponseEntity<SlideshowImageDTO> createImage(@RequestBody SlideshowImageDTO imageDTO) {
        SlideshowImage newImage = new SlideshowImage();
        newImage.setUrl(imageDTO.getUrl());
        newImage.setIndex(imageDTO.getIndex());
        newImage.setAlt(imageDTO.getAlt());

        SlideshowImage createdImage = service.create(newImage);
        return ResponseEntity.ok(SlideshowImageDTO.fromEntity(createdImage));
    }

    // Cập nhật hình ảnh
    @PutMapping("/{id}")
    public ResponseEntity<SlideshowImageDTO> updateImage(@PathVariable String id, @RequestBody SlideshowImageDTO imageDTO) {
        SlideshowImage updatedImage = new SlideshowImage();
        updatedImage.setId(id);
        updatedImage.setUrl(imageDTO.getUrl());
        updatedImage.setIndex(imageDTO.getIndex());
        updatedImage.setAlt(imageDTO.getAlt());

        SlideshowImage savedImage = service.update(id, updatedImage);
        return savedImage != null ? ResponseEntity.ok(SlideshowImageDTO.fromEntity(savedImage)) : ResponseEntity.notFound().build();
    }

    // Xóa hình ảnh
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
