package tdc.edu.vn.project_mobile_be.dtos.requests.jwt;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionRequestDTO {
    String permissionName;
}
