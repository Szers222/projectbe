package tdc.edu.vn.project_mobile_be.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tdc.edu.vn.project_mobile_be.entities.user.UserAddress;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAddressResponseDTO {

    @JsonProperty("addressId")
    UUID addressId;

    @JsonProperty("addressName")
    String addressName;

    @JsonProperty("ward")
    String ward;

    @JsonProperty("district")
    String district;

    @JsonProperty("city")
    String city;

    // Method to map UserAddress to DTO
    public void toDto(UserAddress userAddress) {
        this.addressId = userAddress.getAddressId();
        this.addressName = userAddress.getAddressName();
        this.ward = userAddress.getWard();
        this.district = userAddress.getDistrict();
        this.city = userAddress.getCity();
    }
}
