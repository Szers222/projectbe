package tdc.edu.vn.project_mobile_be.DTO.slideshowDTO;

import tdc.edu.vn.project_mobile_be.entities.slideshows.SlideshowImage;

public class UpdateSlideshowImageDTO {
    private String url;
    private int index;
    private String alt;

    // Constructors
    public UpdateSlideshowImageDTO(String url, int index, String alt) {
        this.url = url;
        this.index = index;
        this.alt = alt;
    }

    // Getters và Setters
    public String getUrl() {
        return url;
    }

    public int getIndex() {
        return index;
    }

    public String getAlt() {
        return alt;
    }

    // Phương thức ánh xạ từ DTO sang entity
    public SlideshowImage toEntity() {
        SlideshowImage image = new SlideshowImage();
        image.setUrl(this.url);
        image.setIndex(this.index);
        image.setAlt(this.alt);
        return image;
    }
}
