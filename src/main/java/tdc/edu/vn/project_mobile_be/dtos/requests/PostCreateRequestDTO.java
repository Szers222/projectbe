package tdc.edu.vn.project_mobile_be.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import tdc.edu.vn.project_mobile_be.commond.customexception.UnsupportedOperationException;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequestDTO implements IDto<Post> {

    @NotBlank(message = "Tên bài viết không được để trống")
    @JsonProperty("postName")
    private String postName;

    @JsonProperty("postContent")
    private String postContent;

    @JsonProperty("postRelease")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    private LocalDate postRelease;

    @NotNull(message = "TypeId không được để trống")
    @JsonProperty("postTypeId")
    private UUID postTypeId;

    @NotNull(message = "StatusId không được để trống")
    @JsonProperty("statusId")
    private UUID postStatusId;

    @NotNull(message = "UserId không được để trống")
    @JsonProperty("userId")
    private UUID postUserId;

    @JsonProperty("postImagePath")
    private String postImagePath;

    @JsonProperty("productId")
    private UUID productId;

    @JsonIgnore
    private Timestamp createdAt;

    @JsonIgnore
    private Timestamp updatedAt;

    @JsonIgnore
    private UUID postId;

    @Override
    public Post toEntity() {
        Post post = new Post();
        BeanUtils.copyProperties(this, post, "postId", "createdAt", "updatedAt");
        return post;
    }

    @Override
    public void toDto(Post entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
