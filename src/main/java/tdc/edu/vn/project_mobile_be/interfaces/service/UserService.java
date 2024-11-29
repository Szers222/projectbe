package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.dtos.requests.user.UpdateCustomerRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.user.UpdateUserRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.user.UserResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface UserService extends IService<User, UUID> {
    List<UserResponseDTO> getAllUsers();
    @Mapping(target = "roles", ignore = true)
    User updateUser(@MappingTarget User userId, UpdateUserRequestDTO request);
    UserResponseDTO getUserById(UUID id);
    UserResponseDTO getMyInfo();
    User updateMyInfo(UpdateCustomerRequestDTO request, MultipartFile userImagePath);
    void deleteUserById(UUID user);

}