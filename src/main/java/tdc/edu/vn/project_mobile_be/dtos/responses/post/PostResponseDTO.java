package tdc.edu.vn.project_mobile_be.dtos.responses.post;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.entities.post.PostComment;
import tdc.edu.vn.project_mobile_be.entities.status.PostStatus;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO implements IDto<Post> {

    @JsonProperty("postName")
    private String postName;
    @JsonProperty("postRelease")
    private String postRelease;
    @JsonProperty("postContent")
    private String postContent;
    @JsonProperty("postImagePath")
    private String postImagePath;
    @JsonProperty("postType")
    private int postType;
    @JsonProperty("userId")
    private User user;
    @JsonProperty("postStatusId")
    private PostStatus postStatus;
    @JsonProperty("postComment")
    private List<PostComment> postCommentList;

    @JsonIgnore
    private UUID postId;
    @JsonIgnore
    private UUID productId;
    @JsonIgnore
    private Timestamp createdAt;
    @JsonIgnore
    private Timestamp updatedAt;


    @Override
    public Post toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(Post entity) {
        BeanUtils.copyProperties(entity, this, "createdAt", "updatedAt");
    }
}