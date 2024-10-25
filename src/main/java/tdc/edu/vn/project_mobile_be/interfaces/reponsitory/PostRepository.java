package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.post.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    Optional<Post> findByPostId(UUID postId);

    List<Post> findAll();

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC LIMIT 10")

    List<Post> findAllByOrderByCreatedAtDesc();


}
