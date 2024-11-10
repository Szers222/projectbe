package tdc.edu.vn.project_mobile_be.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @NotNull(message = "OTP không được để trống")
    @JsonProperty("otp")
    String otp;

    @NotNull(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "mật khẩu không được nhỏ hơn 8 ky tu")
    @JsonProperty("userPassword")
    String userPassword;

}
