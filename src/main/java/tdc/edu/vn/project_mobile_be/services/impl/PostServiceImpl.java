package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ReleaseException;
import tdc.edu.vn.project_mobile_be.dtos.requests.post.PostCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.post.PostUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.post.PostResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.entities.status.PostStatus;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.PostRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.PostStatusRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.PostService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl extends AbService<Post, UUID> implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostStatusRepository postStatusRepository;
    @Autowired
    private UserRepository userRepository;

    private final int POST_STATUS_INACTIVE = 0;
    private final int POST_STATUS_ACTIVE = 1;
    private final int POST_STATUS_DELETED = 2;
    private final int ROLE_ADMIN = 0;
    private final int ROLE_USER = 1;

    @Override
    public Post createPost(PostCreateRequestDTO requestDTO) {

        LocalDateTime releaseDateTime;
        if (requestDTO.getPostRelease() == null) {
            releaseDateTime = LocalDateTime.now();

        } else if (requestDTO.getPostRelease().isAfter(LocalDate.now())
                || requestDTO.getPostRelease().isEqual(LocalDate.now())) {
            releaseDateTime = requestDTO.getPostRelease().atStartOfDay();
        } else {
            throw new ReleaseException("Ngày phát hành không hợp lệ");
        }
        Timestamp releaseTimestamp = Timestamp.valueOf(releaseDateTime);

        PostStatus postStatus = getStatus(requestDTO.getPostStatusId());
        if (postStatus == null) {
            throw new EntityNotFoundException("Post Status thực thể không tồn tại");
        }
        Optional<User> optionalUser = userRepository.findById(requestDTO.getUserId());
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User không tồn tại !");
        }
        User user = optionalUser.get();


        Post post = requestDTO.toEntity();
        post.setPostId(UUID.randomUUID());
        post.setPostRelease(releaseTimestamp);
        post.setPostStatus(postStatus);
        post.setUser(user);

        return postRepository.save(post);
    }

    @Override
    public Post updatePost(PostUpdateRequestDTO requestDTO, UUID postId) {
        Optional<Post> postOptional = postRepository.findByPostId(postId);
        if (postOptional.isEmpty()) {
            throw new EntityNotFoundException("Post thực thể không tồn tại");
        }
        Post post = postOptional.get();
        if (post == null) {
            throw new EntityNotFoundException("Post thực thể không tồn tại");
        }

        LocalDateTime releaseDateTime;
        if (requestDTO.getPostRelease() == null) {
            releaseDateTime = LocalDateTime.now();

        } else if (requestDTO.getPostRelease().isAfter(LocalDate.now())
                || requestDTO.getPostRelease().isEqual(LocalDate.now())) {
            releaseDateTime = requestDTO.getPostRelease().atStartOfDay();
        } else {
            throw new ReleaseException("Ngày phát hành không hợp lệ");
        }
        Timestamp releaseTimestamp = Timestamp.valueOf(releaseDateTime);

        PostStatus postStatus = getStatus(requestDTO.getPostStatusId());
        if (postStatus == null) {
            throw new EntityNotFoundException("Post thực thể không tồn tại");
        }


        post.setPostName(requestDTO.getPostName());
        post.setPostContent(requestDTO.getPostContent());
        post.setPostImagePath(requestDTO.getPostImagePath());
        post.setPostRelease(releaseTimestamp);
        post.setPostStatus(postStatus);
        post.setPostType(requestDTO.getPostType());

        return postRepository.save(post);
    }

    @Override
    public List<PostResponseDTO> findPostByName(String postName) {
        List<Post> listPost = postRepository.findPostByName(postName);
        if (listPost.isEmpty()) {
            throw new ListNotFoundException("Không có bài viết nào");
        }
        List<PostResponseDTO> result = new ArrayList<>();

        listPost.forEach(post -> {
            PostResponseDTO postResponseDTO = new PostResponseDTO();
            postResponseDTO.toDto(post);
            result.add(postResponseDTO);
        });

        return result;

    }

    @Override
    public Post updatePostByProductId(PostUpdateRequestDTO postDTO, UUID productId) {
        Optional<Post> postOptional = postRepository.findPostByProductId(productId);
        if (postOptional.isEmpty()) {
            throw new EntityNotFoundException("Post thực thể không tồn tại");
        }
        Post post = postOptional.get();
        if (post == null) {
            throw new EntityNotFoundException("Post thực thể không tồn tại");
        }

        LocalDateTime releaseDateTime;
        if (postDTO.getPostRelease() == null) {
            releaseDateTime = LocalDateTime.now();

        } else if (postDTO.getPostRelease().isAfter(LocalDate.now())
                || postDTO.getPostRelease().isEqual(LocalDate.now())) {
            releaseDateTime = postDTO.getPostRelease().atStartOfDay();
        } else {
            throw new ReleaseException("Ngày phát hành không hợp lệ");
        }
        Timestamp releaseTimestamp = Timestamp.valueOf(releaseDateTime);

        PostStatus postStatus = getStatus(postDTO.getPostStatusId());
        if (postStatus == null) {
            throw new EntityNotFoundException("Post thực thể không tồn tại");
        }

        post.setPostName(postDTO.getPostName());
        post.setPostContent(postDTO.getPostContent());
        post.setPostImagePath(postDTO.getPostImagePath());
        post.setPostRelease(releaseTimestamp);
        post.setPostStatus(postStatus);
        post.setPostType(postDTO.getPostType());

        return postRepository.save(post);
    }

    @Override
    public List<PostResponseDTO> findAllPostByUserId(UUID userId) {
        List<Post> listPost = postRepository.findAllByUserId(userId);
        if (listPost.isEmpty()) {
            throw new ListNotFoundException("Không có bài viết nào");
        }
        List<PostResponseDTO> result = new ArrayList<>();

        listPost.forEach(post -> {
            PostResponseDTO postResponseDTO = new PostResponseDTO();
            postResponseDTO.toDto(post);
            result.add(postResponseDTO);
        });

        return result;
    }




    @Override
    public boolean deletePost(UUID postId) {
        Optional<Post> optionalPost = postRepository.findByPostId(postId);
        if (optionalPost.isEmpty()) {
            throw new EntityNotFoundException("Post thực thể không tồn tại");
        }
        Post post = optionalPost.get();
        if (post == null) {
            throw new EntityNotFoundException("Post thực thể không tồn tại");
        }
        PostStatus postStatusDelete = postStatusRepository.findByPostStatusType(POST_STATUS_DELETED);
        if (postStatusDelete == null) {
            throw new EntityNotFoundException("PostStatus thực thể không tồn tại");
        }
        post.setPostStatus(postStatusDelete);
        return true;
    }

    @Override
    public Page<PostResponseDTO> findAllPost(int role, Pageable pageable) {
        Page<PostResponseDTO> postPage;

        if (role == ROLE_ADMIN) {
            postPage = postRepository.findAll(pageable).map(post -> {
                PostResponseDTO postResponseDTO = new PostResponseDTO();
                postResponseDTO.toDto(post);
                return postResponseDTO;
            });
        } else if (role == ROLE_USER) {
            postPage = postRepository.findAllByPostStatusType(POST_STATUS_ACTIVE, pageable).map(post -> {
                PostResponseDTO postResponseDTO = new PostResponseDTO();
                postResponseDTO.toDto(post);
                return postResponseDTO;
            });
        } else {
            throw new IllegalArgumentException("Vai trò không hợp lệ");
        }

        if (postPage.isEmpty()) {
            throw new ListNotFoundException("Không có bài viết nào");
        }

        return postPage;
    }


    @Override
    public List<PostResponseDTO> findAllNewPost() {
        List<Post> listPost = postRepository.findAllByOrderByCreatedAtDesc();
        listPost.removeIf(post -> post.getPostStatus().getPostStatusType() == POST_STATUS_DELETED
                || post.getPostStatus().getPostStatusType() == POST_STATUS_INACTIVE);
        listPost.subList(0, 5);
        if (listPost.isEmpty()) {
            throw new ListNotFoundException("Không có bài viết nào");
        }
        List<PostResponseDTO> result = new ArrayList<>();

        listPost.forEach(post -> {
            PostResponseDTO postResponseDTO = new PostResponseDTO();
            postResponseDTO.toDto(post);
            result.add(postResponseDTO);
        });

        return result;
    }


    private PostStatus getStatus(UUID statusId) {
        return postStatusRepository.findByPostStatusId((statusId)) != null ? postStatusRepository.findByPostStatusId((statusId)) : null;
    }


}
