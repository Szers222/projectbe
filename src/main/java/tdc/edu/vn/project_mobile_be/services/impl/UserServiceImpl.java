package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.dtos.requests.user.UpdateCustomerRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.user.UpdateUserRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.user.UserResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import tdc.edu.vn.project_mobile_be.entities.roles.Role;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.IdCardRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.RoleRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.UserService;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service("user_ServiceImpl")
@Primary
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl extends AbService<User, UUID> implements UserService {

    @Autowired
    @Qualifier("userRepository")
    UserRepository userRepository;
    @Autowired
    IdCardRepository idCardRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    GoogleCloudStorageService googleCloudStorageService;



    @Override
    public User updateUser(User user, UpdateUserRequestDTO request) {
        user = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setUserPassword(passwordEncoder.encode(request.getUserPassword()));
        user.setUserPhone(request.getUserPhone());
        user.setUserBirthday(request.getUserBirthday());
        user.setUserPasswordLevel2(passwordEncoder.encode(request.getUserPasswordLevel2()));
        user.setUserImagePath(request.getUserImagePath());
        user.setUserFirstName(request.getUserFirstName());
        user.setUserLastName(request.getUserLastName());
        user.setUserWrongPassword(request.getUserWrongPassword());
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findByRoleName(roleName)
                        .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
                roles.add(role);
            }
            user.setRoles(roles);
        }

        return userRepository.save(user);
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.toDto(user);

        return responseDTO;
    }

    @Override
    public UserResponseDTO getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.toDto(user);
        Set<Cart> carts = user.getCarts();
        carts.forEach(cart -> {
            if (cart.getCartStatus() == 1) {
                responseDTO.setCartId(cart.getCartId());
            }
            if (cart.getCartStatus() == 0) {
                responseDTO.setWishlistId(cart.getCartId());
            }
        });

        return responseDTO;
    }

    @Override
    public User updateMyInfo(UpdateCustomerRequestDTO request, MultipartFile userImagePath) {
        // Lấy email người dùng hiện tại từ SecurityContext
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        // Tìm người dùng dựa trên email
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        // Cập nhật thông tin từ JSON
        user.setUserPhone(request.getUserPhone());
        user.setUserBirthday(request.getUserBirthday());
        user.setUserFirstName(request.getUserFirstName());
        user.setUserLastName(request.getUserLastName());

        // Xử lý ảnh nếu có
        try {
            if (userImagePath != null && !userImagePath.isEmpty()) {
                // Xóa ảnh cũ nếu đã có
                if (user.getUserImagePath() != null) {
                    googleCloudStorageService.deleteFile(user.getUserImagePath());
                }

                // Upload ảnh mới
                String newImagePath = googleCloudStorageService.uploadFile(userImagePath);
                user.setUserImagePath(newImagePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload ảnh: " + e.getMessage());
        }

        return userRepository.save(user);
    }


    @Override
    public void deleteUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not fount"));
        userRepository.delete(user);
    }

    // Get All Users
    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EntityNotFoundException("No users found");
        }
        // Chuyển đổi từ User sang UserResponseDTO
        return users.stream()
                .map(user -> {
                    UserResponseDTO responseDTO = new UserResponseDTO();
                    responseDTO.toDto(user);
                    return responseDTO;
                })
                .collect(Collectors.toList());
    }
}