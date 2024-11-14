package tdc.edu.vn.project_mobile_be.dtos.requests.jwt;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogoutRequestDTO {
    String token;
}
