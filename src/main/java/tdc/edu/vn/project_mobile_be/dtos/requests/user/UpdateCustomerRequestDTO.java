package tdc.edu.vn.project_mobile_be.dtos.requests.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCustomerRequestDTO {

    @NotEmpty(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Số điện thoại không hợp lệ")
    @JsonProperty("userPhone")
    String userPhone;

    @JsonProperty("userBirthday")
    @NotEmpty(message = "userBirthday không được để trống")
    @NotNull(message = "userBirthday not null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Timestamp userBirthday;

    @NotEmpty(message = "Họ không được để trống")
    @NotNull(message = "userLastName not null")
    @JsonProperty("userLastName")
    String userLastName;

    @NotEmpty(message = "Tên không được để trống")
    @NotNull(message = "userFirstName not null")
    @JsonProperty("userFirstName")
    String userFirstName;

}



