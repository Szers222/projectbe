package tdc.edu.vn.project_mobile_be.DTO.slideshowDTO;

import tdc.edu.vn.project_mobile_be.entities.slideshows.SlideshowImage;

import java.time.LocalDateTime;

public class GetSlideshowImageDTO {
    private String id;
    private String url;
    private int index;
    private String alt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public GetSlideshowImageDTO(String id, String url, int index, String alt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.url = url;
        this.index = index;
        this.alt = alt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters và Setters
    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getIndex() {
        return index;
    }

    public String getAlt() {
        return alt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Phương thức ánh xạ từ entity sang DTO
    public static GetSlideshowImageDTO fromEntity(SlideshowImage entity) {
        return new GetSlideshowImageDTO(
                entity.getId(),
                entity.getUrl(),
                entity.getIndex(),
                entity.getAlt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
