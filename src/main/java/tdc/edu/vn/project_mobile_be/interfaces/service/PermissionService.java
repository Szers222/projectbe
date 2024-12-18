package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.mapstruct.Mapper;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.PermissionRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.permisstion.PermissionResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.permissions.Permission;

@Mapper(componentModel = "spring")
public interface PermissionService {
    Permission toPermission(PermissionRequestDTO request);
    PermissionResponseDTO toPermissionResponse(Permission permission);

}
