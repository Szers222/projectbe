package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.entities.slideshows.SlideshowImage;
import tdc.edu.vn.project_mobile_be.repositories.SlideshowImageRepository;
import tdc.edu.vn.project_mobile_be.services.SlideshowImageService;

import java.util.List;
import java.util.Optional;

@Service
public class SlideshowImageServiceImpl implements SlideshowImageService {

    @Autowired
    private SlideshowImageRepository repository;

    //lay tat cả
    @Override
    public List<SlideshowImage> getAll() {
        return repository.findAll();
    }
    //chi tiết
    @Override
    public SlideshowImage getById(String id) {
        Optional<SlideshowImage> image = repository.findById(id);
        return image.orElse(null);
    }
    //thêm
    @Override
    public SlideshowImage create(SlideshowImage image) {
        return repository.save(image);
    }
    //update
    @Override
    public SlideshowImage update(String id, SlideshowImage updatedImage) {
        Optional<SlideshowImage> existingImageOpt = repository.findById(id);
        if (existingImageOpt.isPresent()) {
            SlideshowImage existingImage = existingImageOpt.get();
            // Bảo toàn created_at
            updatedImage.setCreatedAt(existingImage.getCreatedAt());
            updatedImage.setId(id);  // Bảo toàn id
            // Save lại đối tượng đã cập nhật
            return repository.save(updatedImage);
        }
        return null;
    }

    //xóa
    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
