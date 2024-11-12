package tdc.edu.vn.project_mobile_be.interfaces.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import tdc.edu.vn.project_mobile_be.dtos.requests.post.PostCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.post.PostUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.post.PostResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.post.Post;

import java.util.List;
import java.util.UUID;


public interface PostService {
    Post createPost(PostCreateRequestDTO requestDTO);

    Post updatePost(PostUpdateRequestDTO requestDTO, UUID postId);

    List<PostResponseDTO> findPostByName(String postName);

    boolean deletePost(UUID postId);

    Page<PostResponseDTO> findAllPost(int role, Pageable pageable);

    List<PostResponseDTO> findAllNewPost();


    Post updatePostByProductId(PostUpdateRequestDTO postDTO, UUID productId);

    List<PostResponseDTO> findAllPostByUserId(UUID userId);
}
