package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.dtos.requests.PostCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.PostRemoveRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.entities.status.PostStatus;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.PostRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.PostStatusRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.PostService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl extends AbService<Post, UUID> implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostStatusRepository repository;


    @Override
    public Post createPost(PostCreateRequestDTO requestDTO) {

        LocalDateTime releaseDateTime;
        if (requestDTO.getPostRelease() == null) {
            releaseDateTime = LocalDateTime.now();

        } else {
            releaseDateTime = requestDTO.getPostRelease().atStartOfDay();
        }
        Timestamp releaseTimestamp = Timestamp.valueOf(releaseDateTime);

        PostStatus postStatus = getStatus(requestDTO.getPostStatusId());
        if (postStatus == null) {
            throw new EntityNotFoundException("Post thực thể không tồn tại");
        }

        Post post = requestDTO.toEntity();
        post.setPostId(UUID.randomUUID());
        post.setPostRelease(releaseTimestamp);
        post.setPostStatus(postStatus);

        return postRepository.save(post);
    }

    @Override
    public boolean deletePost(PostRemoveRequestDTO requestDTO) {
        Post post = postRepository.findPostByPostId(requestDTO.getId());
        if (post == null) {
            throw new EntityNotFoundException("Post thực thể không tồn tại");
        }
        postRepository.delete(post);


        return true;
    }

    @Override
    public Post updatePost(PostCreateRequestDTO requestDTO) {
        return null;
    }

    @Override
    public List<Post> getAllPost() {
        return List.of();
    }

    private PostStatus getStatus(UUID statusId) {
        return repository.findByPostStatusId((statusId)) != null ? repository.findByPostStatusId((statusId)) : null;
    }

}
