package tdc.edu.vn.project_mobile_be.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.user.User;


import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDTO {

    @JsonProperty("userId")
    UUID userId;

    @JsonProperty("userEmail")
    String userEmail;

    @JsonProperty("userPhone")
    String userPhone;

    @JsonProperty("userBirthday")
    Timestamp userBirthday;

    @JsonProperty("userAddress")
    String userAddress;

    @JsonProperty("userImagePath")
    String userImagePath;

    @JsonProperty("userPasswordLevel2")
    String userPasswordLevel2;

    @JsonProperty("userLastName")
    String userLastName;

    @JsonProperty("userFirstName")
    String userFirstName;

    @JsonProperty("userMoney")
    Double userMoney;

    @JsonProperty("userPoint")
    int userPoint;

    @JsonProperty("userWrongPassword")
    int userWrongPassword;

    @JsonProperty("iCard")
    IdCardResponseDTO iCard;

    @JsonProperty("createdAt")
    Timestamp createdAt;

    @JsonProperty("updatedAt")
    Timestamp updatedAt;

    Set<RoleResponseDTO> roles;

    // Phương thức để chuyển đổi từ Entity sang DTO
    public void toDto(User user) {
        // Sao chép các thuộc tính từ entity sang DTO, ngoại trừ createdAt và updatedAt
        BeanUtils.copyProperties(user, this, "createdAt", "updatedAt", "roles");

        // Xử lý thẻ ID nếu có
        if (user.getICard() != null) {
            this.iCard = new IdCardResponseDTO();
            this.iCard.toDto(user.getICard());
        }

        // Lấy createdAt và updatedAt từ entity
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();

        // Chuyển đổi roles từ Entity sang DTO
        if (user.getRoles() != null) {
            this.roles = user.getRoles().stream()
                    .map(role -> {
                        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
                        BeanUtils.copyProperties(role, roleResponseDTO);
                        return roleResponseDTO;
                    })
                    .collect(Collectors.toSet());
        }
    }

}