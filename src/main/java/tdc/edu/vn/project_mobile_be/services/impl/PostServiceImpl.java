package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.PostCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.PostRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.PostService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PostServiceImpl extends AbService<Post, UUID> implements PostService {
    @Autowired
    private PostRepository postRepository;


    @Override
    public Post createPost(PostCreateRequestDTO requestDTO) {
        LocalDateTime releaseDateTime;
        if (requestDTO.getPostRelease() == null) {
            releaseDateTime = LocalDateTime.now();

        } else {
            releaseDateTime = requestDTO.getPostRelease().atStartOfDay();
        }
        return null;
    }
}
