package tdc.edu.vn.project_mobile_be.DTO;

import tdc.edu.vn.project_mobile_be.entities.idcards.IdCard;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.time.LocalDateTime;

public class UserDTO {
    private String userId;
    private String email;
    private String password; // Thêm trường password
    private String firstName;
    private String lastName;
    private String phone;
    private String birthday;
    private String address;
    private String imagePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private IdCard idCard;

    public UserDTO() {
    }

    public UserDTO(String userId, String email, String password, String firstName, String lastName, String phone, String birthday, String address, String imagePath, LocalDateTime createdAt, LocalDateTime updatedAt, IdCard idCard) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.idCard = idCard;
    }


    // Getters và Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public IdCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCard idCard) {
        this.idCard = idCard;
    }

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getBirthday(),
                user.getAddress(),
                user.getImagePath(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getIdCard()
        );
    }


}
