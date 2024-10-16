package tdc.edu.vn.project_mobile_be.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    // Method to convert Entity to DTO
    public void toDto(User user) {
        this.userId = user.getUserId();
        this.userEmail = user.getUserEmail();
        this.userPhone = user.getUserPhone();
        this.userBirthday = user.getUserBirthday();
        this.userAddress = user.getUserAddress();
        this.userImagePath = user.getUserImagePath();
        this.userPasswordLevel2 = user.getUserPasswordLevel2();
        this.userLastName = user.getUserLastName();
        this.userFirstName = user.getUserFirstName();
        this.userMoney = user.getUserMoney();
        this.userPoint = user.getUserPoint();
        this.userWrongPassword = user.getUserWrongPassword();
        if (user.getICard() != null) {
            this.iCard = new IdCardResponseDTO();
            this.iCard.toDto(user.getICard());
        }
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}