package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.dtos.requests.slideshowimage.SlideshowImageCreateDTO;
import tdc.edu.vn.project_mobile_be.entities.slideshow.SlideshowImage;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.UUID;

public interface SlideshowImageService extends IService<SlideshowImage, UUID> {
    SlideshowImage createSlideshowImage(SlideshowImageCreateDTO params, MultipartFile file);
}