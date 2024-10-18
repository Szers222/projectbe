package tdc.edu.vn.project_mobile_be.dtos.requests;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequestDTO implements IDto<Post> {

    @NotBlank
    @NotNull(message = "Tên Bài Viết không được để trống")
    @JsonProperty("postName")
    private String postName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    @JsonProperty("postRelease")
    private LocalDate postRelease;

    @JsonProperty("postContent")
    private String postContent;

    @JsonProperty("postImagePath")
    private String postImagePath;

//    @NotNull(message = "User ID không được để trống")
    @JsonProperty("userId")
    private UUID userId;

    @JsonProperty("postStatusId")
    private UUID postStatusId;




    @JsonIgnore
    private Timestamp createdAt;
    @JsonIgnore
    private Timestamp updatedAt;
    @JsonIgnore
    private Product product;


    @Override
    public Post toEntity() {
        Post entity = new Post();
        BeanUtils.copyProperties(this, entity, "id", "createdAt", "updatedAt");
        return entity;
    }

    @Override
    public void toDto(Post entity) {
        throw new UnsupportedOperationException("Unimplemented method 'toDTO' in CreatePostRequestDTO");
    }
}
