package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.RoleRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.UpdateRoleRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.role.RoleResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.roles.Role;

@Mapper(componentModel = "spring")
public interface RoleService {
    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleRequestDTO request);

    @Mapping(target = "permissionsName", source = "permissions")
    RoleResponseDTO roleResponse(Role role);

}
