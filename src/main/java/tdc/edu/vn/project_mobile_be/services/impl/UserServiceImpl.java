package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateUserRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.UserResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.UserService;
import tdc.edu.vn.project_mobile_be.entities.status.UserStatus;
import tdc.edu.vn.project_mobile_be.entities.roles.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    // Create User
//    @Override
//    public UserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO) {
//        User user = createUserRequestDTO.toEntity();
//        User savedUser = userRepository.save(user);
//
//        UserResponseDTO userResponseDTO = new UserResponseDTO();
//        userResponseDTO.toDto(savedUser);
//        return userResponseDTO;
//    }

    // Get All Users
    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new EntityNotFoundException("No users found");
        }
        return users.stream()
                .map(user -> {
                    UserResponseDTO responseDTO = new UserResponseDTO();
                    responseDTO.toDto(user);
                    return responseDTO;
                })
                .collect(Collectors.toList());
    }

}
