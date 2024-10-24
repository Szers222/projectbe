package tdc.edu.vn.project_mobile_be.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {

    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @JsonProperty("userEmail")
    private String userEmail;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    @JsonProperty("userPassword")
    private String userPassword;

    @NotEmpty(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Số điện thoại không hợp lệ")
    @JsonProperty("userPhone")
    private String userPhone;

    @JsonProperty("userBirthday")
    @Min(value = 1990, message = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp userBirthday;

    @JsonProperty("userAddress")
    private String userAddress;

    @JsonProperty("userImagePath")
    private String userImagePath;

    @JsonProperty("userPasswordLevel2")
    private String userPasswordLevel2;

    @NotEmpty(message = "Họ không được để trống")
    @JsonProperty("userLastName")
    private String userLastName;

    @NotEmpty(message = "Tên không được để trống")
    @JsonProperty("userFirstName")
    private String userFirstName;

    @NotNull(message = "Số tiền không được để trống")
    @Min(value = 0, message = "Số tiền phải lớn hơn hoặc bằng 0")
    @JsonProperty("userMoney")
    private Double userMoney;

    @JsonProperty("userPoint")
    private int userPoint;

    @JsonProperty("userWrongPassword")
    private int userWrongPassword;

    @JsonProperty("icardId")
    private UUID iCardId;

    @JsonProperty("statusId")
    private UUID statusId;

    // Method to convert DTO to Entity
    public User toEntity() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
