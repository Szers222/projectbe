package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.slideshowimage.SlideshowImageCreateDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.slideshowimage.SlideshowImageUpdateDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.slideshow.SlideshowResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.slideshow.SlideshowImage;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface SlideshowImageService extends IService<SlideshowImage, UUID> {
    SlideshowImage createSlideshowImage(SlideshowImageCreateDTO params);
    SlideshowImage updateSlideshowImage(SlideshowImageUpdateDTO params, UUID id);

    void deleteSlideshowImage(UUID id);

    List<SlideshowResponseDTO> getSlideshowImage(String content);
}
