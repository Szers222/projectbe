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


    @JsonProperty("userPassword")
    String userPassword;

    @JsonProperty("userPhone")
    String userPhone;

    @JsonProperty("userBirthday")
    Timestamp userBirthday;


    @JsonProperty("userPasswordLevel2")
    String userPasswordLevel2;

    @JsonProperty("userLastName")
    String userLastName;

    @JsonProperty("userFirstName")
    String userFirstName;

    @JsonProperty("userWrongPassword")
    int userWrongPassword;

    List<String> roles;
}

