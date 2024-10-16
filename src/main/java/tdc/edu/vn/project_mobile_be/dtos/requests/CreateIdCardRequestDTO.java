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



@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIdCardRequestDTO {

    @NotEmpty(message = "Không đuợc để trống")
    @NotBlank(message = "Số CMND không được để trống")
    @JsonProperty("idCardNumber")
    @NotNull(message = "Không được để null")
    private String idCardNumber;

    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Ảnh CMND không được để trống")
    @JsonProperty("idCardNumber")
    @NotNull(message = "khong duoc null")
    private String imageFrontPath;

    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Ảnh CMND không được để trống")
    @JsonProperty("idCardNumber")
    @NotNull(message = "Không được để null")
    private String imageBackPath;

    @NotBlank(message = "Ngày CMND khong de trong")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonProperty("idCardDate")
    @NotNull(message = "Không được để null")
    private String idCardDate;


    public IdCard toEntity() {
        IdCard idCard = new IdCard();
        BeanUtils.copyProperties(this, idCard);
        return idCard;
    }
}
