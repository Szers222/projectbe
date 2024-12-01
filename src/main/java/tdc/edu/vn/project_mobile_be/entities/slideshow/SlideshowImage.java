package tdc.edu.vn.project_mobile_be.entities.slideshow;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.product.Product;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "slideshow_images")
public class SlideshowImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "slideshow_image_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID slideShowImageId;

    @Column(name = "slideshow_image_url", nullable = false, columnDefinition = "VARCHAR(255)")
    private String slideShowImageImageURL;

    @Column(name = "slideshow_image_index", nullable = false, columnDefinition = "INTEGER")
    private Integer slideShowImageImageIndex;

    @Column(name = "slideshow_image_alt", length = 255, columnDefinition = "VARCHAR(255)")
    private String slideShowImageImageAlt;

    @ManyToMany(mappedBy = "slideshowImages")
    @ToString.Exclude
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<Product> products = new HashSet<>();


    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;
}
