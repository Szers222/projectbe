package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tdc.edu.vn.project_mobile_be.dtos.requests.RoleRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.RoleResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.roles.Role;

@Mapper(componentModel = "spring")
public interface RoleService {
    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleRequestDTO request);

    @Mapping(target = "permissionsName", source = "permissions")
    RoleResponseDTO roleResponse(Role role);

}
