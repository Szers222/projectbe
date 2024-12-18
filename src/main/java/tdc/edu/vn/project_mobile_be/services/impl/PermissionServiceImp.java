package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.PermissionRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.permisstion.PermissionResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.permissions.Permission;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.PermissionRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.PermissionService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImp implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    public PermissionResponseDTO create(PermissionRequestDTO request) {
        Permission permission = this.toPermission(request);
        permission = permissionRepository.save(permission);
        return this.toPermissionResponse(permission);
    }
    public List<PermissionResponseDTO> getAll(){
        return permissionRepository.findAll()
                .stream()
                .map(this::toPermissionResponse)
                .toList();
    }
    public void delete(UUID id){
        permissionRepository.deleteById(id);
    }

    @Override
    public Permission toPermission(PermissionRequestDTO request) {
        if (request != null) {
            Permission permission = new Permission();
            permission.setPermissionName(request.getPermissionName());
            return permission;
        }
        return null;
    }

    @Override
    public PermissionResponseDTO toPermissionResponse(Permission permission) {
        if (permission != null) {
            return PermissionResponseDTO.builder()
                    .permissionId(permission.getPermissionId())
                    .permissionName(permission.getPermissionName())
                    .build();
        }
        return null;
    }

}
