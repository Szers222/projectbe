package tdc.edu.vn.project_mobile_be.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.user.User;


import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    @JsonProperty("userId")
    private UUID userId;

    @JsonProperty("userEmail")
    private String userEmail;

    @JsonProperty("userPhone")
    private String userPhone;

    @JsonProperty("userBirthday")
    private Timestamp userBirthday;

    @JsonProperty("userAddress")
    private String userAddress;

    @JsonProperty("userImagePath")
    private String userImagePath;

    @JsonProperty("userPasswordLevel2")
    private String userPasswordLevel2;

    @JsonProperty("userLastName")
    private String userLastName;

    @JsonProperty("userFirstName")
    private String userFirstName;

    @JsonProperty("userMoney")
    private Double userMoney;

    @JsonProperty("userPoint")
    private int userPoint;

    @JsonProperty("userWrongPassword")
    private int userWrongPassword;

    @JsonProperty("iCard")
    private IdCardResponseDTO iCard;

    @JsonProperty("createdAt")
    private Timestamp createdAt;

    @JsonProperty("updatedAt")
    private Timestamp updatedAt;

    // Phương thức để chuyển đổi từ Entity sang DTO
    public void toDto(User user) {
        // Sao chép các thuộc tính từ entity sang DTO, ngoại trừ createdAt và updatedAt
        BeanUtils.copyProperties(user, this, "createdAt", "updatedAt");

        // Xử lý thẻ ID nếu có
        if (user.getICard() != null) {
            this.iCard = new IdCardResponseDTO();
            this.iCard.toDto(user.getICard());
        }

        // Lấy createdAt và updatedAt từ entity
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}