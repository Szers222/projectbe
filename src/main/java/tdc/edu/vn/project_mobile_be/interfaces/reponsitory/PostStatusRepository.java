package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tdc.edu.vn.project_mobile_be.entities.status.PostStatus;

import java.util.UUID;

public interface PostStatusRepository extends JpaRepository<PostStatus, UUID> {
    PostStatus findByPostStatusId(UUID postStatusId);
}
