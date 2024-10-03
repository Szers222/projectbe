package tdc.edu.vn.project_mobile_be.entities.slideshows;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "slideshow_images")
public class SlideshowImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "slideshow_image_id", length = 36)
    private String id;

    @Column(name = "slideshow_image_url", length = 255)
    private String url;

    @Column(name = "slideshow_image_index")
    private int index;

    @Column(name = "slideshow_image_alt", length = 255)
    private String alt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters và Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
