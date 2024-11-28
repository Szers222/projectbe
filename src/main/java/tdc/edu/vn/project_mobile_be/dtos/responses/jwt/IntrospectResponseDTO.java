package tdc.edu.vn.project_mobile_be.dtos.responses.jwt;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntrospectResponseDTO {
    boolean valid;
}
