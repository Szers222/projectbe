package tdc.edu.vn.project_mobile_be.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionResponseDTO {
    UUID permissionId;
    String permissionName;
}
