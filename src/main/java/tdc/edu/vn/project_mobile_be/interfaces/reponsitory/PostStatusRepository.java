package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tdc.edu.vn.project_mobile_be.entities.status.PostStatus;

import java.util.UUID;

public interface PostStatusRepository extends JpaRepository<PostStatus, UUID> {
    PostStatus findByPostStatusId(UUID postStatusId);

    @Query("SELECT ps FROM PostStatus ps WHERE ps.postStatusType = :postStatusType")
    PostStatus findByPostStatusType(@Param("postStatusType") int postStatusType);
}
