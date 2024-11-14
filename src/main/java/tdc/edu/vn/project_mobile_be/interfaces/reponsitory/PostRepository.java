package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.post.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    Optional<Post> findByPostId(UUID postId);

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC LIMIT 10")
    List<Post> findAllByOrderByCreatedAtDesc();

    @Query("select p from Post p left join fetch p.product pr where pr.productId = :productId")
    Optional<Post> findPostByProductId(@Param("productId") UUID productId);

    @Query("SELECT p FROM Post p left join fetch p.postStatus ps WHERE ps.postStatusType = :postStatusType")
    Page<Post> findAllByPostStatusType(@Param("postStatusType") int postStatusType, Pageable pageable);

    @Query("SELECT p FROM Post p left join fetch p.user u WHERE u.userId = :userId")
    List<Post> findAllByUserId(@Param("userId") UUID userId);

    @Query("SELECT p FROM Post p where p.postName like %:postName%")
    List<Post> findPostByName(@Param("postName") String postName);
}
