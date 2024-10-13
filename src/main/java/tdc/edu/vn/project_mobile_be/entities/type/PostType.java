package tdc.edu.vn.project_mobile_be.entities.type;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.post.Post;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "post_type")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class PostType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_type_id",nullable = false,columnDefinition = "BINARY(16)")
    private UUID postTypeId;
    @Column(name = "post_type",nullable = false)
    private int postType;
    @Column(name = "post_type_name",nullable = false)
    private String postTypeName;

    @OneToMany(mappedBy = "postType")
    private Set<Post> posts = new HashSet<>();

}
