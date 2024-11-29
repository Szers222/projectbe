package tdc.edu.vn.project_mobile_be.dtos.requests.shipper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShipperRequestDTO {
    @NotEmpty(message = "khong de trong")
    @JsonProperty("userId")
    User userId;
}
