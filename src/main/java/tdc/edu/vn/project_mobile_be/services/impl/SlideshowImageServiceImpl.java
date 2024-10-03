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

    @Override
    public List<SlideshowImage> getAll() {
        return repository.findAll();
    }

    @Override
    public SlideshowImage getById(String id) {
        Optional<SlideshowImage> image = repository.findById(id);
        return image.orElse(null);
    }

    @Override
    public SlideshowImage create(SlideshowImage image) {
        return repository.save(image);
    }

    @Override
    public SlideshowImage update(String id, SlideshowImage updatedImage) {
        if (repository.existsById(id)) {
            updatedImage.setId(id);
            return repository.save(updatedImage);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
