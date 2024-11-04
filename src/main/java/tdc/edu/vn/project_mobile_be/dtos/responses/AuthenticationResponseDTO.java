package tdc.edu.vn.project_mobile_be.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponseDTO {
    String token;
    boolean authenticated;
    List<UUID> roles;
}
