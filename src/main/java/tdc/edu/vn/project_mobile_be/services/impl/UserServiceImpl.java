package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class UserServiceImpl extends AbService<User, UUID> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;
    @Autowired
    private IdCardRepository idCardRepository;
    // Tạo người dùng
    @Override
    public User createUser(CreateUserRequestDTO userRequestDTO) {

        // Kiểm tra xem email đã tồn tại chưa
        // if (userRepository.existsByEmail(userRequestDTO.getUserEmail())) {
        //     throw new ExistsEmailException("Email đã tồn tại!");
        // }

        // Kiểm tra xem số điện thoại đã tồn tại chưa
        // if (userRepository.existsByUserPhone(userRequestDTO.getUserPhone())) {
        //     throw new ExistsPhoneException("Số điện thoại đã tồn tại!");
        // }

        IdCard idCard1 = idCardRepository.findByCardId(userRequestDTO.getICardId());
        UserStatus userStatus = userStatusRepository.findByUserStatusId(userRequestDTO.getStatusId());
        System.out.printf("sdfdsfds" + userStatus);
        // Tạo đối tượng User từ DTO
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setUserEmail(userRequestDTO.getUserEmail());
        user.setUserPassword(userRequestDTO.getUserPassword());
        user.setUserPhone(userRequestDTO.getUserPhone());
        user.setUserLastName(userRequestDTO.getUserLastName());
        user.setUserFirstName(userRequestDTO.getUserFirstName());
        user.setUserMoney(userRequestDTO.getUserMoney());
        user.setUserPoint(userRequestDTO.getUserPoint());
        user.setUserWrongPassword(userRequestDTO.getUserWrongPassword());
        user.setUserBirthday(userRequestDTO.getUserBirthday());
        user.setUserAddress(userRequestDTO.getUserAddress());
        user.setUserImagePath(userRequestDTO.getUserImagePath());
        user.setUserPasswordLevel2(userRequestDTO.getUserPasswordLevel2());
        user.setICard(idCard1);
        user.setUserStatus(userStatus);

//        // Thiết lập UserStatus, nếu statusId không null
//        if (userRequestDTO.getStatusId() != null) {
//            UserStatus userStatus = userStatusRepository.findById(userRequestDTO.getStatusId())
//                    .orElseThrow(() -> new EntityNotFoundException("Status không tồn tại!"));
//            user.setUserStatus(userStatus); // Thiết lập đối tượng UserStatus cho đối tượng User
//        } else {
//            user.setUserStatus(null); // Hoặc xử lý theo cách khác nếu statusId là null
//        }

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
