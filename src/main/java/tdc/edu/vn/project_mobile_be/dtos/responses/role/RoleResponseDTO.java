package tdc.edu.vn.project_mobile_be.dtos.responses.role;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tdc.edu.vn.project_mobile_be.dtos.responses.permisstion.PermissionResponseDTO;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponseDTO {
    UUID roleId;
    String roleName;
    Set<PermissionResponseDTO> permissionsName;
}
