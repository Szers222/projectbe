package tdc.edu.vn.project_mobile_be.entities.type;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.post.Post;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "post_status")
@DynamicInsert
@DynamicUpdate
public class PosType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_type_id",nullable = false,columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(name = "post_type_type",nullable = false)
    private int type;
    @Column(name = "post_type_name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "type",cascade = CascadeType.ALL,orphanRemoval = false)
    private List<Post> posts;

}
