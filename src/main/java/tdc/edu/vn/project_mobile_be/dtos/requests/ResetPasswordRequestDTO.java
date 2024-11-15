package tdc.edu.vn.project_mobile_be.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordRequestDTO {

    @NotNull(message = "Mật khẩu không được để trống")
    @NotEmpty(message = "Chua nhap password")
    @Size(min = 8, message = "mật khẩu không được nhỏ hơn 8 ky tu")
    @JsonProperty("userPassword")
    String userPassword;

    @NotNull(message = "Xac nhan Passwrod không được để trống")
    @NotEmpty(message = "Chua xac nhan lai password")
    @Size(min = 8, message = "mật khẩu không được nhỏ hơn 8 ky tu")
    @JsonProperty("confirmPassword")
    String confirmPassword;


}
