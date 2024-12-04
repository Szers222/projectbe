package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.slideshowimage.SlideshowImageCreateDTO;
import tdc.edu.vn.project_mobile_be.entities.slideshow.SlideshowImage;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.SlideshowRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.SlideshowImageService;

import java.util.UUID;

@Service
public class SlideshowImageServiceImpl extends AbService<SlideshowImage, UUID> implements SlideshowImageService {

    @Autowired
    private SlideshowRepository slideshowRepository;

    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;


    @Override
    public SlideshowImage createSlideshowImage(SlideshowImageCreateDTO params) {
        if (params.getSlideShowImageImageIndex() < 0) {
            throw new IllegalArgumentException("Image index must be greater than 0");
        }
        if (params.getSlideShowImageImageIndex() > 5) {
            throw new IllegalArgumentException("Image index must be less than 5");
        }
        if (params.getImagePath() == null) {
            throw new IllegalArgumentException("Image must not be null");
        }
        try {
            String imagePath = googleCloudStorageService.uploadFile(params.getImagePath());
            SlideshowImage slideshowImage = params.toEntity();
            slideshowImage.setSlideShowImageId(UUID.randomUUID());
            slideshowImage.setSlideShowImageImagePath(imagePath);
            return slideshowRepository.save(slideshowImage);
        } catch (Exception e) {
            throw new IllegalArgumentException("Image upload failed");
        }

    }

}
