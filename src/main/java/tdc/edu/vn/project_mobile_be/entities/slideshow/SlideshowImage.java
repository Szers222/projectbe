package tdc.edu.vn.project_mobile_be.entities.slideshow;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "slideshow_images")
public class SlideshowImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "slideshow_image_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID slideshowImageId;

    @Column(name = "slideshow_image_url", nullable = false, columnDefinition = "VARCHAR(255)")
    private String slideshowImageUrl;

    @Column(name = "slideshow_image_index", nullable = false, columnDefinition = "INTEGER")
    private Integer slideshowImageIndex;

    @Column(name = "slideshow_image_alt", length = 255, columnDefinition = "VARCHAR(255)")
    private String slideshowImageAlt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;
}
