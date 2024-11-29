package tdc.edu.vn.project_mobile_be.dtos.requests.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAddressRequestDTO {
    @NotEmpty(message = "Địa chỉ không được để trống")
    @JsonProperty("userAddress")
    String userAddress;

    @NotEmpty(message = "Xã/Phường không được để trống")
    @JsonProperty("ward")
    String ward;

    @NotEmpty(message = "Quận/Huyện không được để trống")
    @JsonProperty("district")
    String district;

    @NotEmpty(message = "Thành phố không được để trống")
    @JsonProperty("city")
    String city;

    @NotNull(message = "User ID không được để trống")
    @JsonProperty("userId")
    UUID userId;
}

