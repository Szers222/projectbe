package tdc.edu.vn.project_mobile_be.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import org.springframework.beans.BeanUtils;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIdCardRequestDTO {

    @NotEmpty
    @NotBlank(message = "Số CMND không được để trống")
    @JsonProperty("idCardNumber")
    private String idCardNumber;

    @NotBlank(message = "Ảnh trươớc không được để trôngs")
    @JsonProperty("imageFrontPath")
    private String imageFrontPath;

    @NotBlank(message = "Ảnh sau khong de trong")
    @JsonProperty("imageBackPath")
    private String imageBackPath;

    @NotBlank(message = "Ảnh sau khong de trong")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonProperty("idCardDate")
    private String idCardDate;


    public IdCard toEntity() {
        IdCard idCard = new IdCard();
        BeanUtils.copyProperties(this, idCard);
        return idCard;
    }
}
