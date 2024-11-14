package tdc.edu.vn.project_mobile_be.dtos.requests.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCustomerRequestDTO {
    @NotEmpty(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "PASSWORD_EXISTED")
    @JsonProperty("userPassword")
    String userPassword;
    @NotEmpty(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Số điện thoại không hợp lệ")
    @JsonProperty("userPhone")
    String userPhone;
    @JsonProperty("userBirthday")
    @Min(value = 1900, message = "")
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
    @JsonProperty("userWrongPassword")
    int userWrongPassword;
    UUID idCards;
}
