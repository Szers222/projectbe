package tdc.edu.vn.project_mobile_be.entities.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.util.UUID;

@Data
@Entity
@Table(name = "post_comments")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class PostComment {

    @Id
    @Column(name = "post_commnet_id", nullable = false, columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID postCommentId;

    @Lob
    @Column(name = "post_comment_content", columnDefinition = "TEXT")
    private String postCommentContent;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
