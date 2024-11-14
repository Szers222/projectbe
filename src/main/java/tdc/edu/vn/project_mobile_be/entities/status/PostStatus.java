package tdc.edu.vn.project_mobile_be.entities.status;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.post.Post;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "post_status")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class PostStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_status_id",nullable = false,columnDefinition = "BINARY(16)")
    private UUID postStatusId;
    @Column(name = "post_status_type",nullable = false)
    private int postStatusType;
    @Column(name = "post_status_name",nullable = false)
    private String postStatusName;

    @OneToMany(mappedBy = "postStatus")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Set<Post> posts= new HashSet<>();
}
