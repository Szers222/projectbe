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

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequestDTO implements IDto<Post> {

    @JsonProperty("post-name")
    String name;
    @JsonProperty("post-slug")
    String slug;
    @JsonProperty("post-release")
    String release;
    @JsonProperty("post-content")
    String content;
    @JsonProperty("post-image-path")
    String image_path;
    @JsonProperty("post-status")
    String status;
    @JsonProperty("post-type")
    String type;
    @JsonProperty("user-name")
    String user_name;
    @JsonProperty("post-status-id")
    UUID postStatusId;
    @JsonProperty("post-type-id")
    UUID postTypeId;

    @JsonIgnore
    String created_at;
    @JsonIgnore
    String updated_at;
    @JsonIgnore
    PostStatus postStatus;
    @JsonIgnore
    PostType postType;
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
