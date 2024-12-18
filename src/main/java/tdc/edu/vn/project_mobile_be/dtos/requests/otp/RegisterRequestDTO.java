package tdc.edu.vn.project_mobile_be.dtos.requests.otp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.dtos.requests.user.CreateAddressRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import javax.validation.constraints.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequestDTO {
    @NotNull(message = "Mật khẩu không được để trống")
    @NotEmpty(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "mật khẩu không được nhỏ hơn 8 ky tu")
    @JsonProperty("userPassword")
    String userPassword;

    @NotNull(message = "So dien thoai khong duoc de trong")
    @NotEmpty(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Số điện thoại không hợp lệ")
    @JsonProperty("userPhone")
    String userPhone;

    @NotEmpty(message = "Khong duoc de trong ngay sinh")
    @JsonProperty("userBirthday")
    @Min(value = 1990, message = "")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Timestamp userBirthday;


    @NotEmpty(message = "Họ không được để trống")
    @JsonProperty("userLastName")
    String userLastName;

    @NotEmpty(message = "Tên không được để trống")
    @JsonProperty("userFirstName")
    String userFirstName;

    @NotNull(message = "Thông tin địa chỉ không được để trống")
    @JsonProperty("address")
    CreateAddressRequestDTO address;


    // Method to convert DTO to Entity
    public User toEntity() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
