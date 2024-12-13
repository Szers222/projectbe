package tdc.edu.vn.project_mobile_be.dtos.requests.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordRequestDTO {
    @NotEmpty(message = "Password ko de trong")
    @NotNull(message = "Password not null")
    @Length(min = 8,message = "toi thieu 8 ki tu")
    @JsonProperty("oldPassword")
    String oldPassword;

    @NotEmpty(message = "Password khong de trong")
    @NotNull(message = "Password not null")
    @Length(min = 8,message = "toi thieu 8 ki tu")
    @JsonProperty("userPassword")
    String userPassword;
}
