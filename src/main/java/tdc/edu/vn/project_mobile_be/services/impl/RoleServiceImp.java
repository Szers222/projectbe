package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.RoleRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.PermissionResponseDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImp implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;




    public RoleResponseDTO create(RoleRequestDTO request) {
        var role = this.toRole(request);

        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissionsId()));
        role.setPermissions(permissions);

        role = roleRepository.save(role);
        return this.roleResponse(role);
    }
    public List<RoleResponseDTO> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(this::roleResponse)
                .toList();
    }
    public void delete(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Role not found"));
        roleRepository.delete(role);
    }

    @Override
    public Role toRole(RoleRequestDTO request) {
        if (request != null) {
            Role role = new Role();
            role.setRoleName(request.getRoleName());

            Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissionsId()));
            role.setPermissions(permissions);

            return role;
        }
        return null;
    }

    @Override
    public RoleResponseDTO roleResponse(Role role) {
        if (role != null) {
            RoleResponseDTO responseDTO = new RoleResponseDTO();
            responseDTO.setRoleName(role.getRoleName());
            responseDTO.setRoleId(role.getRoleId());
            Set<PermissionResponseDTO> permissionsName = role.getPermissions()
                    .stream()
                    .map(permission -> {
                        PermissionResponseDTO permissionResponseDTO = new PermissionResponseDTO();
                        permissionResponseDTO.setPermissionName(permission.getPermissionName());
                        permissionResponseDTO.setPermissionId(permission.getPermissionId());
                        return permissionResponseDTO;
                    })
                    .collect(Collectors.toSet());
            responseDTO.setPermissionsName(permissionsName);

            return responseDTO;
        }
        return null;
    }

}
