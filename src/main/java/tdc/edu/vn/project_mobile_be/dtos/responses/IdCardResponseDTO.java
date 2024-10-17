package tdc.edu.vn.project_mobile_be.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdCardResponseDTO {


    @JsonProperty("cardId")
    private UUID cardId;
    @JsonProperty("idCardNumber")
    private String idCardNumber;
    @JsonProperty("imageFrontPath")
    private String imageFrontPath;
    @JsonProperty("imageBackPath")
    private String imageBackPath;
    @JsonProperty("idCardDate")
    private String idCardDate;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Phương thức để chuyển đổi từ entity sang DTO
    public void toDto(IdCard idCard) {
        // Sao chép các thuộc tính từ entity sang DTO
        BeanUtils.copyProperties(idCard, this, "createdAt", "updatedAt");
        this.cardId = idCard.getCardId(); // Lấy cardId từ entity
        this.createdAt = idCard.getCreatedAt(); // Lấy createdAt từ entity
        this.updatedAt = idCard.getUpdatedAt(); // Lấy updatedAt từ entity
    }
}
