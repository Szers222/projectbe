package tdc.edu.vn.project_mobile_be.entities.idcards;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.io.Serializable;

@Entity
@Table(name = "idcards")
public class IdCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ididcard_id", length = 36)
    private String idCardId;

    @Column(name = "idcard_number", length = 12, nullable = false)
    private String idCardNumber;

    @Column(name = "idcard_image_front_path")
    private String imageFrontPath;

    @Column(name = "idcard_image_back_path")
    private String imageBackPath;

    @Column(name = "idcard_date", length = 10)
    private String idCardDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    // Getters and Setters
    public String getIdCardId() {
        return idCardId;
    }

    public void setIdCardId(String idCardId) {
        this.idCardId = idCardId;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getImageFrontPath() {
        return imageFrontPath;
    }

    public void setImageFrontPath(String imageFrontPath) {
        this.imageFrontPath = imageFrontPath;
    }

    public String getImageBackPath() {
        return imageBackPath;
    }

    public void setImageBackPath(String imageBackPath) {
        this.imageBackPath = imageBackPath;
    }

    public String getIdCardDate() {
        return idCardDate;
    }

    public void setIdCardDate(String idCardDate) {
        this.idCardDate = idCardDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
