package tdc.edu.vn.project_mobile_be.dtos.requests.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequestDTO {
    @NotEmpty(message = "Password khong de trong")
    @NotNull(message = "Password not null")
    @Min(value = 8,message = "toi thieu 8 ki tu")
    @Max(value = 25,message = "toi da 25 ki tu")
    @JsonProperty("userPassword")
    String userPassword;

    @NotEmpty(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Số điện thoại không hợp lệ")
    @JsonProperty("userPhone")
    String userPhone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "userBirthday not null")
    @NotEmpty(message = "ngay sinh not null")
    @JsonProperty("userBirthday")
    Timestamp userBirthday;

    @NotEmpty(message = "Password khong de trong")
    @NotNull(message = "Password not null")
    @Min(value = 8,message = "toi thieu 8 ki tu")
    @Max(value = 25,message = "toi da 25 ki tu")
    @JsonProperty("userPasswordLevel2")
    String userPasswordLevel2;

    @NotEmpty(message = "userLastName khong de trong")
    @NotNull(message = "userLastName not null")
    @JsonProperty("userLastName")
    String userLastName;

    @NotEmpty(message = "userFirstName khong de trong")
    @NotNull(message = "userFirstName not null")
    @JsonProperty("userFirstName")
    String userFirstName;

    @NotEmpty(message = "userWrongPassword khong de trong")
    @NotNull(message = "userWrongPassword not null")
    @Min(value = 6,message = "toi thieu 6 ki tu")
    @Max(value = 6,message = "toi da 6 ki tu")
    @JsonProperty("userWrongPassword")
    int userWrongPassword;

    List<String> roles;
}

