package tdc.edu.vn.project_mobile_be.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CartStatusResponseDTO {
    String cartStatusName; // Tên trạng thái
    int cartStatusType; // Loại trạng thái
}
