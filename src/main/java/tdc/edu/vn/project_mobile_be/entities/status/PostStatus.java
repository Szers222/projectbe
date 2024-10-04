package tdc.edu.vn.project_mobile_be.entities.status;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.entities.post.Post;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "post_status")
@DynamicInsert
@DynamicUpdate
public class PostStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_status_id",nullable = false,columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "post_status_type",nullable = false)
    private int type;
    @Column(name = "post_status_name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "status",cascade = CascadeType.ALL,orphanRemoval = false)
    private List<Post> posts;
}
