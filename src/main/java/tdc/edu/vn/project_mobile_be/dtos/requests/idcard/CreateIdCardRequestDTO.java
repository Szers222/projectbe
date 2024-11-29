package tdc.edu.vn.project_mobile_be.dtos.requests.idcard;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIdCardRequestDTO {

    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Số CMND không được để trống")
    @JsonProperty("idCardNumber")
    @NotNull(message = "Không được để null")
    private String idCardNumber;

    @JsonProperty("imageFront")
    private MultipartFile imageFront;

    @JsonProperty("imageBack")
    private MultipartFile imageBack;

    @NotBlank(message = "Ngày cấp không được để trống")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("idCardDate")
    @NotNull(message = "Không được để null")
    private String idCardDate;
}

