package tdc.edu.vn.project_mobile_be.dtos.requests.jwt;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequestDTO {
    String roleName;
    Set<UUID> permissionsId;
}
