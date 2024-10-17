package tdc.edu.vn.project_mobile_be.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIdCardRequestDTO {

    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Số CMND không được để trống")
    @JsonProperty("idCardNumber")
    @NotNull(message = "Không được để null")
    private String idCardNumber;  // Số CMND/ID Card Number

    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Ảnh mặt trước không được để trống")
    @JsonProperty("imageFrontPath")
    @NotNull(message = "Không được để null")
    private String imageFrontPath;  // Đường dẫn ảnh mặt trước

    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Ảnh mặt sau không được để trống")
    @JsonProperty("imageBackPath")
    @NotNull(message = "Không được để null")
    private String imageBackPath;  // Đường dẫn ảnh mặt sau

    @NotBlank(message = "Ngày cấp không được để trống")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonProperty("idCardDate")
    @NotNull(message = "Không được để null")
    private String idCardDate;  // Ngày cấp ID Card


    public IdCard toEntity() {
        IdCard idCard = new IdCard();
        BeanUtils.copyProperties(this, idCard);
        return idCard;
    }
}
