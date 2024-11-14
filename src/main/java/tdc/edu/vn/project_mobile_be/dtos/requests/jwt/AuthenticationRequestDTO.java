package tdc.edu.vn.project_mobile_be.dtos.requests.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequestDTO {
    @NotEmpty(message = "Email không được để trống")
    @Email(message = "USERNAME_EXISTED")
    @JsonProperty("userEmail")
    String userEmail;
    @NotEmpty(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "PASSWORD_EXISTED")
    @JsonProperty("userPassword")
    String userPassword;
}
