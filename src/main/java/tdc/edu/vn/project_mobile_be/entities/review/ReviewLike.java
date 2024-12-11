package tdc.edu.vn.project_mobile_be.entities.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "review_like")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ReviewLike {
    @Id
    @Column(name = "review_like_id",nullable = false,columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID reviewLikeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id", nullable = false, foreignKey = @ForeignKey(name = "fk_review_like_review"))
    @JsonIgnore
    private Review review;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

}
