package tdc.edu.vn.project_mobile_be.services;

import tdc.edu.vn.project_mobile_be.entities.slideshows.SlideshowImage;
import java.util.List;

public interface SlideshowImageService {
    List<SlideshowImage> getAll();
    SlideshowImage getById(String id);
    SlideshowImage create(SlideshowImage image);
    SlideshowImage update(String id, SlideshowImage image);
    void delete(String id);
}
