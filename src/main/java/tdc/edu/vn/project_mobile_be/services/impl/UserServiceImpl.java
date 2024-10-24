package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ExistsEmailException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ExistsPhoneException;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateUserRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.UserResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.IdCardRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserStatusRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.IdCardService;
import tdc.edu.vn.project_mobile_be.interfaces.service.UserService;
import tdc.edu.vn.project_mobile_be.entities.status.UserStatus;
import tdc.edu.vn.project_mobile_be.entities.roles.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("user_ServiceImpl")
@Primary
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl extends AbService<User, UUID> implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserStatusRepository userStatusRepository;
    @Autowired
    IdCardRepository idCardRepository;
    @Autowired
    UserService userService;
    // Tạo người dùng
    @Override
    public User createUser(CreateUserRequestDTO userRequestDTO) {
        IdCard idCard1 = idCardRepository.findByCardId(userRequestDTO.getICardId());
        UserStatus userStatus = userStatusRepository.findByUserStatusId(userRequestDTO.getStatusId());
        // Tạo đối tượng User từ DTO
        User user = userService.createUser(userRequestDTO);
        user.setICard(idCard1);
        user.setUserStatus(userStatus);
        //Ma hoa mat khau
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = passwordEncoder.encode(userRequestDTO.getUserPassword());
        String encodedPasswordLevel2 = passwordEncoder.encode(userRequestDTO.getUserPasswordLevel2());
        user.setUserPassword(encodedPassword);
        user.setUserPasswordLevel2(encodedPasswordLevel2);
        // Lưu đối tượng User vào CSDL
        return userRepository.save(user);
    }


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
