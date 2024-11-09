package tdc.edu.vn.project_mobile_be.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterResponseDTO {
    @JsonProperty("userEmail")
    String userEmail;
    @JsonProperty("userPhone")
    String userPhone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Timestamp userBirthday;
    @JsonProperty("userAddress")
    String userAddress;
    @JsonProperty("userLastName")
    String userLastName;
    @JsonProperty("userFirstName")
    String userFirstName;
}
