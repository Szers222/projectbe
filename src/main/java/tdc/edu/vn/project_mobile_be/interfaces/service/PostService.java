package tdc.edu.vn.project_mobile_be.interfaces.service;


import tdc.edu.vn.project_mobile_be.dtos.requests.PostCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.PostRemoveRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.post.Post;

import java.util.List;


public interface PostService {
    Post createPost(PostCreateRequestDTO requestDTO);

    boolean deletePost(PostRemoveRequestDTO requestDTO);

    Post updatePost(PostCreateRequestDTO requestDTO);

    List<Post> getAllPost();
}
