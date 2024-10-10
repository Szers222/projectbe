package tdc.edu.vn.project_mobile_be.interfaces.service;



import tdc.edu.vn.project_mobile_be.dtos.requests.CreatePostRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.post.Post;


public interface PostService {
    Post createPost(CreatePostRequestDTO createPostRequestDTO);
    boolean existsBySlug(String slug);
}
