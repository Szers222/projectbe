package tdc.edu.vn.project_mobile_be.services;

import tdc.edu.vn.project_mobile_be.entities.user.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(String id);
    User createUser(User user);
    User updateUser(String id, User user);
    void deleteUser(String id);
}
