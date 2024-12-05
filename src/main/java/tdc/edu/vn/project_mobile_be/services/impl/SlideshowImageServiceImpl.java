package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.dtos.requests.slideshowimage.SlideshowImageCreateDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.slideshowimage.SlideshowImageUpdateDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.slideshow.SlideshowResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.slideshow.SlideshowImage;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.SlideshowRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.SlideshowImageService;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public SlideshowImage updateSlideshowImage(SlideshowImageUpdateDTO params, UUID id) {
        if (params.getSlideShowImageImageIndex() < 0) {
            throw new IllegalArgumentException("Image index must be greater than 0");
        }
        if (params.getImagePath() == null) {
            throw new IllegalArgumentException("Image must not be null");
        }
        SlideshowImage slideshowImage = slideshowRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Image not found"));
        try {
            String imagePath = googleCloudStorageService.updateFile(params.getImagePath(), slideshowImage.getSlideShowImageImagePath());
            if (imagePath == null) {
                throw new IllegalArgumentException("Image update failed");
            }
            slideshowImage.setSlideShowImageImageAlt(params.getSlideShowImageImageAlt());
            slideshowImage.setSlideShowImageImageIndex(params.getSlideShowImageImageIndex());
            slideshowImage.setSlideShowImageUrl(params.getSlideShowImageUrl());
            slideshowImage.setSlideShowContent(params.getSlideShowContent());
            slideshowImage.setSlideShowImageImagePath(imagePath);
            return slideshowRepository.save(slideshowImage);
        } catch (Exception e) {
            throw new IllegalArgumentException("Image upload failed");
        }
    }

    @Override
    public void deleteSlideshowImage(UUID id) {
        SlideshowImage slideshowImage = slideshowRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Image not found"));
        googleCloudStorageService.deleteFile(slideshowImage.getSlideShowImageImagePath());
        slideshowRepository.delete(slideshowImage);
    }

    @Override
    public List<SlideshowResponseDTO> getSlideshowImage(String content) {
        List<SlideshowImage> slideshowImages = slideshowRepository.findByContent(content);
        if (slideshowImages.isEmpty()) {
            throw new ListNotFoundException("Image not found");
        }
        List<SlideshowResponseDTO> slideshowResponseDTOS = new ArrayList<>();
        slideshowImages.forEach(slideshowImage -> {
            SlideshowResponseDTO slideshowResponseDTO = new SlideshowResponseDTO();
            slideshowResponseDTO.toDto(slideshowImage);
            slideshowResponseDTOS.add(slideshowResponseDTO);
        });
        return slideshowResponseDTOS;
    }

}
