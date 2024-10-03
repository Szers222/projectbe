package tdc.edu.vn.project_mobile_be.DTO;

import tdc.edu.vn.project_mobile_be.entities.idcards.IdCard;

import java.time.LocalDateTime;

public class IdCardDTO {
    private String idCardId;
    private String idCardNumber;
    private String imageFrontPath;
    private String imageBackPath;
    private String idCardDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public IdCardDTO() {
    }

    public IdCardDTO(String idCardId, String idCardNumber, String imageFrontPath, String imageBackPath, String idCardDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idCardId = idCardId;
        this.idCardNumber = idCardNumber;
        this.imageFrontPath = imageFrontPath;
        this.imageBackPath = imageBackPath;
        this.idCardDate = idCardDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters và Setters
    public String getIdCardId() {
        return idCardId;
    }

    public void setIdCardId(String idCardId) {
        this.idCardId = idCardId;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getImageFrontPath() {
        return imageFrontPath;
    }

    public void setImageFrontPath(String imageFrontPath) {
        this.imageFrontPath = imageFrontPath;
    }

    public String getImageBackPath() {
        return imageBackPath;
    }

    public void setImageBackPath(String imageBackPath) {
        this.imageBackPath = imageBackPath;
    }

    public String getIdCardDate() {
        return idCardDate;
    }

    public void setIdCardDate(String idCardDate) {
        this.idCardDate = idCardDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Phương thức chuyển đổi từ entity IdCard sang DTO
    public static IdCardDTO fromEntity(IdCard idCard) {
        return new IdCardDTO(
                idCard.getIdCardId(),
                idCard.getIdCardNumber(),
                idCard.getImageFrontPath(),
                idCard.getImageBackPath(),
                idCard.getIdCardDate(),
                idCard.getCreatedAt(),  // Thêm createdAt
                idCard.getUpdatedAt()   // Thêm updatedAt
        );
    }
}
