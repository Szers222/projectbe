package tdc.edu.vn.project_mobile_be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.DTO.UserDTO;
import tdc.edu.vn.project_mobile_be.entities.idcards.IdCard;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Hiển thị tất cả người dùng
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Lấy thông tin người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Thêm người dùng mới cùng với thẻ ID
    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setBirthday(userDTO.getBirthday());
        user.setAddress(userDTO.getAddress());
        user.setImagePath(userDTO.getImagePath());

        // Nếu có IdCard, thiết lập nó
        if (userDTO.getIdCard() != null) {
            IdCard idCard = new IdCard();
            idCard.setIdCardNumber(userDTO.getIdCard().getIdCardNumber());
            idCard.setImageFrontPath(userDTO.getIdCard().getImageFrontPath());
            idCard.setImageBackPath(userDTO.getIdCard().getImageBackPath());
            idCard.setIdCardDate(userDTO.getIdCard().getIdCardDate());
            idCard.setUser(user);  // Thiết lập quan hệ
            user.setIdCard(idCard);
        }

        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // Cập nhật người dùng
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        User existingUser = userService.getUserById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setPassword(userDTO.getPassword());
            existingUser.setFirstName(userDTO.getFirstName());
            existingUser.setLastName(userDTO.getLastName());
            existingUser.setPhone(userDTO.getPhone());
            existingUser.setBirthday(userDTO.getBirthday());
            existingUser.setAddress(userDTO.getAddress());
            existingUser.setImagePath(userDTO.getImagePath());

            // Cập nhật IdCard nếu có
            if (userDTO.getIdCard() != null) {
                IdCard idCard = existingUser.getIdCard();
                if (idCard == null) {
                    idCard = new IdCard();
                    existingUser.setIdCard(idCard);
                }
                idCard.setIdCardNumber(userDTO.getIdCard().getIdCardNumber());
                idCard.setImageFrontPath(userDTO.getIdCard().getImageFrontPath());
                idCard.setImageBackPath(userDTO.getIdCard().getImageBackPath());
                idCard.setIdCardDate(userDTO.getIdCard().getIdCardDate());
                idCard.setUser(existingUser);
            }

            // Gọi phương thức updateUser với ID và User
            User updatedUser = userService.updateUser(id, existingUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Xóa người dùng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
