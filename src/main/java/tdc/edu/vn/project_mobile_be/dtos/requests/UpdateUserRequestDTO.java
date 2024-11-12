package tdc.edu.vn.project_mobile_be.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tdc.edu.vn.project_mobile_be.entities.roles.Role;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequestDTO {
    @NotEmpty(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "PASSWORD_EXISTED")
    @JsonProperty("userPassword")
    String userPassword;

    @NotEmpty(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Số điện thoại không hợp lệ")
    @JsonProperty("userPhone")
    String userPhone;

    @JsonProperty("userBirthday")
    @Min(value = 1990, message = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Timestamp userBirthday;

    @JsonProperty("userAddress")
    String userAddress;

    @JsonProperty("userImagePath")
    String userImagePath;

    @JsonProperty("userPasswordLevel2")
    String userPasswordLevel2;

    @NotEmpty(message = "Họ không được để trống")
    @JsonProperty("userLastName")
    String userLastName;

    @NotEmpty(message = "Tên không được để trống")
    @JsonProperty("userFirstName")
    String userFirstName;

    @NotNull(message = "Số tiền không được để trống")
    @Min(value = 0, message = "Số tiền phải lớn hơn hoặc bằng 0")
    @JsonProperty("userMoney")
    Double userMoney;

    @JsonProperty("userPoint")
    int userPoint;

    @JsonProperty("userWrongPassword")
    int userWrongPassword;

    @JsonProperty("icardId")
    UUID iCardId;

    Set<Role> roles;

}
