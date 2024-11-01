package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateUserRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.UpdateUserRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.UserResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface UserService extends IService<User, UUID> {
    List<UserResponseDTO> getAllUsers();
    User createUser(CreateUserRequestDTO userRequestDTO);
    @Mapping(target = "roles", ignore = true)
    User updateUser(@MappingTarget UUID userId, UpdateUserRequestDTO request);
}