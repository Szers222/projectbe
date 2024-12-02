package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.slideshow.SlideshowImage;

import java.util.UUID;

@Repository
public interface SlideshowRepository extends JpaRepository<SlideshowImage, UUID> {
}
