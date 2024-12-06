package tdc.edu.vn.project_mobile_be.dtos.responses.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.dtos.responses.idcard.IdCardResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.role.RoleResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;


import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDTO implements IDto<User> {

    @JsonProperty("userId")
    UUID userId;

    @JsonProperty("userEmail")
    String userEmail;

    @JsonProperty("userPhone")
    String userPhone;

    @JsonProperty("userBirthday")
    Timestamp userBirthday;

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

    @JsonProperty("cartId")
    UUID cartId;

    @JsonProperty("cartBuyNowId")
    UUID cartBuyNowId;

    @JsonProperty("wishListId")
    UUID wishlistId;

    @JsonProperty("createdAt")
    Timestamp createdAt;

    @JsonProperty("updatedAt")
    Timestamp updatedAt;

    @JsonProperty("roles")
    Set<RoleResponseDTO> roles;

    @JsonProperty("address")
    UserAddressResponseDTO address;

    @Override
    public User toEntity() {
        return null;
    }

    @Override
    public void toDto(User user) {
        // Sao chép các thuộc tính từ entity sang DTO, ngoại trừ createdAt và updatedAt
        BeanUtils.copyProperties(user, this, "createdAt", "updatedAt", "roles");

        // Xử lý thẻ ID nếu có
        if (user.getICard() != null) {
            this.iCard = new IdCardResponseDTO();
            this.iCard.toDto(user.getICard());
        }
        if (user.getDetail() != null) {
            this.address = new UserAddressResponseDTO();
            this.address.toDto(user.getDetail());
        }
        // Lấy createdAt và updatedAt từ entity
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();

        // Chuyển đổi roles từ Entity sang DTO
        if (user.getRoles() != null) {
            log.info("Role" + user.getRoles());
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