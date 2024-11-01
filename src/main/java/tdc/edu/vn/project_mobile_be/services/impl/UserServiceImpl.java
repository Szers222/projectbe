package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.AppException;
import tdc.edu.vn.project_mobile_be.commond.ErrorCode;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ExistsEmailException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ExistsPhoneException;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateUserRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.UpdateUserRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.UserResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.IdCardRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.RoleRepository;
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
    private RoleRepository roleRepository;

    // Tạo người dùng
    @Override
    public User createUser(CreateUserRequestDTO userRequestDTO) {
        IdCard idCard1 = idCardRepository.findByCardId(userRequestDTO.getICardId());
        UserStatus userStatus = userStatusRepository.findByUserStatusId(userRequestDTO.getStatusId());

        // Tạo đối tượng User từ DTO mà không cần sử dụng `userService.createUser`
        User user = new User();
        user.setICard(idCard1);
        user.setUserStatus(userStatus);

        user.setUserEmail(userRequestDTO.getUserEmail());
        user.setUserPhone(userRequestDTO.getUserPhone());
        user.setUserLastName(userRequestDTO.getUserLastName());
        user.setUserFirstName(userRequestDTO.getUserFirstName());
        user.setUserBirthday(userRequestDTO.getUserBirthday());
        user.setUserAddress(userRequestDTO.getUserAddress());
        user.setUserImagePath(userRequestDTO.getUserImagePath());
        user.setUserMoney(userRequestDTO.getUserMoney());
        user.setUserPoint(userRequestDTO.getUserPoint());
        user.setUserWrongPassword(userRequestDTO.getUserWrongPassword());

        // Mã hóa mật khẩu
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = passwordEncoder.encode(userRequestDTO.getUserPassword());
        String encodedPasswordLevel2 = passwordEncoder.encode(userRequestDTO.getUserPasswordLevel2());
        user.setUserPassword(encodedPassword);
        user.setUserPasswordLevel2(encodedPasswordLevel2);

        UUID roleId = UUID.fromString("9e50a2d4-d2cc-46e3-a040-f3da335309fd"); // Thay thế bằng UUID của vai trò ADMIN
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role != null) {
            user.getRoles().add(role);
        }

        // Lưu đối tượng User vào CSDL
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UUID userId, UpdateUserRequestDTO request) {
        IdCard idCard1 = idCardRepository.findByCardId(request.getICardId());
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_EXISTED));
        user = new User();
        user.setICard(idCard1);

        user.setUserPassword(request.getUserPassword());
        user.setUserPasswordLevel2(request.getUserPasswordLevel2());
        user.setUserPhone(request.getUserPhone());
        user.setUserBirthday(request.getUserBirthday());
        user.setUserAddress(request.getUserAddress());
        user.setUserImagePath(request.getUserImagePath());
        user.setUserLastName(request.getUserLastName());
        user.setUserFirstName(request.getUserFirstName());
        user.setUserMoney(request.getUserMoney());
        user.setUserPoint(request.getUserPoint());
        user.setUserWrongPassword(request.getUserWrongPassword());
        user.setRoles(request.getRoles());

        // Mã hóa mật khẩu
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = passwordEncoder.encode(request.getUserPassword());
        String encodedPasswordLevel2 = passwordEncoder.encode(request.getUserPasswordLevel2());
        user.setUserPassword(encodedPassword);
        user.setUserPasswordLevel2(encodedPasswordLevel2);

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
