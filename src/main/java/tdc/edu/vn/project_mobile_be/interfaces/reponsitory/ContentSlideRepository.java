package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.contentslide.Content;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ContentSlideRepository extends JpaRepository<Content, UUID> {
    List<Content> findByStatus(String status);
}

