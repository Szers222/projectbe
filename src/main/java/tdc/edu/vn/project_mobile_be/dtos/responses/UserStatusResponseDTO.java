package tdc.edu.vn.project_mobile_be.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import tdc.edu.vn.project_mobile_be.entities.status.UserStatus;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusResponseDTO implements IDto<UserStatus> {
    @JsonProperty("name")
    private String userStatusName;

    @JsonProperty("type")
    private int userStatusType;

    @JsonIgnore
    private UUID userStatusId;

    @Override
    public UserStatus toEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void toDto(UserStatus entity) {
        BeanUtils.copyProperties(entity, this, "userStatusId");
    }
}

