package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.RoleRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.RoleResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.permissions.Permission;
import tdc.edu.vn.project_mobile_be.entities.roles.Role;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.PermissionRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.RoleRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.RoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImp {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleService roleService;



    public RoleResponseDTO create(RoleRequestDTO request) {
        var role = roleService.toRole(request);

        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissionsId()));
        role.setPermissions(permissions);

        role = roleRepository.save(role);
        return roleService.roleResponse(role);
    }
    public List<RoleResponseDTO> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleService::roleResponse)
                .toList();
    }
    public void delete(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Role not found"));
        roleRepository.delete(role);
    }

}
