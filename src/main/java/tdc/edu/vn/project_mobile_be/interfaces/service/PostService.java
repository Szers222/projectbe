package tdc.edu.vn.project_mobile_be.interfaces.service;


import tdc.edu.vn.project_mobile_be.dtos.requests.post.PostCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.post.PostUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.post.PostResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.post.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public interface PostService {
    Post createPost(PostCreateRequestDTO requestDTO);

    Post updatePost(PostUpdateRequestDTO requestDTO, UUID postId);

    Post findPostById(UUID postId);

    boolean deletePost(UUID postId);

    List<Post> findAllPost();

    List<PostResponseDTO> findAllNewPost();
}
