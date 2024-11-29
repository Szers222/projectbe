package tdc.edu.vn.project_mobile_be.controllers;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ApiResponse;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.RoleRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.UpdateRoleRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.role.RoleResponseDTO;
import tdc.edu.vn.project_mobile_be.services.impl.RoleServiceImp;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleServiceImp roleServiceImp;

    @PostMapping("/role")
    ApiResponse<RoleResponseDTO> create(@RequestBody RoleRequestDTO request) {
        return ApiResponse.<RoleResponseDTO>builder()
                .result(roleServiceImp.create(request))
                .build();
    }
    @GetMapping("/role")
    ApiResponse<List<RoleResponseDTO>> getAll(){
        return ApiResponse.<List<RoleResponseDTO>>builder()
                .result(roleServiceImp.findAll())
                .build();
    }
    @DeleteMapping("/role/{id}")
    ApiResponse<Void> delete(@PathVariable UUID id) {
        roleServiceImp.delete(id);
        return ApiResponse.<Void>builder().build();
    }
    
}
