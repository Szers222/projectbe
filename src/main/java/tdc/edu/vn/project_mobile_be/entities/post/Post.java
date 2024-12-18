package tdc.edu.vn.project_mobile_be.entities.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.status.PostStatus;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Post {
    @Id
    @Column(name = "post_id", nullable = false,columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID postId;

    @Column(name = "post_name", columnDefinition = "VARCHAR(255)")
    private String postName;

    @Column(name = "post_release", columnDefinition = "TIMESTAMP", nullable = false)
    private Timestamp postRelease;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "post_type", columnDefinition = "INT")
    private int postType;

    @Lob
    @Column(name = "post_content", columnDefinition = "TEXT")
    private String postContent;

    @Column(name = "post_image_path", columnDefinition = "VARCHAR(255)")
    private String postImagePath;

    @ManyToOne
    @JoinColumn(name = "post_status_id", nullable = false)
    private PostStatus postStatus;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "post")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference(value = "user-post")
    private User user;
}
