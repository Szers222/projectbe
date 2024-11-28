package tdc.edu.vn.project_mobile_be.controllers;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ApiResponse;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.PermissionRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.permisstion.PermissionResponseDTO;
import tdc.edu.vn.project_mobile_be.services.impl.PermissionServiceImp;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionServiceImp permissionServiceImp;

    @PostMapping("/permission")
    ApiResponse<PermissionResponseDTO> create(@RequestBody PermissionRequestDTO request) {
        return ApiResponse.<PermissionResponseDTO>builder()
                .result(permissionServiceImp.create(request))
                .build();
    }
    @GetMapping("/permission")
    ApiResponse<List<PermissionResponseDTO>> getAll(){
        return ApiResponse.<List<PermissionResponseDTO>>builder()
                .result(permissionServiceImp.getAll())
                .build();
    }
    @DeleteMapping("/permission/{id}")
    ApiResponse<Void> delete(@PathVariable UUID id) {
        permissionServiceImp.delete(id);
        return ApiResponse.<Void>builder().build();
    }

}
