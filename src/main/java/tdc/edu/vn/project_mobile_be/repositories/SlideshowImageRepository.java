package tdc.edu.vn.project_mobile_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tdc.edu.vn.project_mobile_be.entities.slideshows.SlideshowImage;

public interface SlideshowImageRepository extends JpaRepository<SlideshowImage, String> {
}
