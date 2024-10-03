package tdc.edu.vn.project_mobile_be.DTO;

import tdc.edu.vn.project_mobile_be.entities.slideshows.SlideshowImage;

import java.time.LocalDateTime;

public class SlideshowImageDTO {
    private String url;
    private int index;
    private String alt;

    public SlideshowImageDTO() {
    }

    public SlideshowImageDTO(String url, int index, String alt) {
        this.url = url;
        this.index = index;
        this.alt = alt;
    }

    // Getters và Setters
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

    public static SlideshowImageDTO fromEntity(SlideshowImage slideshowImage) {
        return new SlideshowImageDTO(
                slideshowImage.getUrl(),
                slideshowImage.getIndex(),
                slideshowImage.getAlt()
        );
    }
}
