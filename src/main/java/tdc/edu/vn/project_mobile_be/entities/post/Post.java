package tdc.edu.vn.project_mobile_be.entities.post;

import jakarta.jws.soap.SOAPBinding;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.status.PostStatus;
import tdc.edu.vn.project_mobile_be.entities.type.PostType;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.sql.Timestamp;
import java.util.UUID;

@Data
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
    private UUID id;

    @Column(name = "post_name", columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "post_slug", columnDefinition = "VARCHAR(255)")
    private String slug;

    @Column(name = "post_release", columnDefinition = "TIMESTAMP", nullable = false)
    private Timestamp release;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;


    @Lob
    @Column(name = "post_content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "post_image_path", columnDefinition = "VARCHAR(255)")
    private String image_path;

    @ManyToOne
    @JoinColumn(name = "post_status_id", nullable = false)
    private PostStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_type_id", nullable = false)
    private PostType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
