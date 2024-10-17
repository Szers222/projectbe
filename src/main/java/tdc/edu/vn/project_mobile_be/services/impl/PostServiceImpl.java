package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreatePostRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.PostRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.PostService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(CreatePostRequestDTO params) {

        LocalDateTime releaseDateTime;
        if (params.getPostRelease() == null) {
            releaseDateTime = LocalDateTime.now();

        } else {
            releaseDateTime = params.getPostRelease().atStartOfDay();
        }
        return null;
    }
}
