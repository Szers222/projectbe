package tdc.edu.vn.project_mobile_be.dtos.requests;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.status.PostStatus;
import tdc.edu.vn.project_mobile_be.entities.type.PostType;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequestDTO implements IDto<Post> {

    @JsonProperty("postName")
    public String postName;
    @JsonProperty("postSlug")
    public String postSlug;
    @JsonProperty("postRelease")
    public Timestamp postRelease;
    @JsonProperty("postContent")
    public String postContent;
    @JsonProperty("postImagePath")
    public String postImagePath;
    @JsonProperty("postStatus")
    public String postStatus;
    @JsonProperty("postType")
    public String postType;
    @JsonProperty("userName")
    public String userName;
    @JsonProperty("postStatusId")
    UUID postStatusId;
    @JsonProperty("postTypeId")
    UUID postTypeId;

    @JsonIgnore
    String createdAt;
    @JsonIgnore
    String updatedAt;
    @JsonIgnore
    Product product;


    @Override
    public Post toEntity() {
        Post entity = new Post();
        BeanUtils.copyProperties(this, entity, "postStatusId", "postTypeId");
        return entity;
    }

    @Override
    public void toDto(Post entity) {
        throw new UnsupportedOperationException("Unimplemented method 'toDTO' in CreatePostRequestDTO");
    }
}
