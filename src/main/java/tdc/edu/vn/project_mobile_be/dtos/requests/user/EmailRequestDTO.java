package tdc.edu.vn.project_mobile_be.dtos.requests.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailRequestDTO {
    @NotNull(message = "Email không được để trống")
    @NotEmpty(message = "Chua nhap Email")
    @Email(message = "không đúng định danng email")
    @JsonProperty("userEmail")
    String userEmail;
}
