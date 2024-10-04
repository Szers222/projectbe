package tdc.edu.vn.project_mobile_be.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.idcards.IdCard;
import tdc.edu.vn.project_mobile_be.entities.one_time_password.OneTimePassword;
import tdc.edu.vn.project_mobile_be.entities.status.UserStatus;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "user_email", nullable = false, columnDefinition = "VARCHAR(255)")
    private String userEmail;

    @Column(name = "user_password", nullable = false, columnDefinition = "VARCHAR(255)")
    private String userPassword;


    @Column(name = "user_phone", nullable = false, columnDefinition = "VARCHAR(15)")
    private String userPhone;

    @Column(name = "user_birthday", columnDefinition = "TIMESTAMP")
    private Timestamp userBirthday;

    @Column(name = "user_address", columnDefinition = "TEXT", nullable = true)
    private String userAddress;

    @Column(name = "user_image_path", columnDefinition = "VARCHAR(255)")
    private String userImagePath;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "user_password_2", nullable = true, columnDefinition = "VARCHAR(255)")
    private String userPassword2;

    @Column(name = "user_last_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String userLastName;

    @Column(name = "user_first_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String userFirstName;

    @Column(name = "user_money", nullable = false, columnDefinition = "DOUBLE")
    private Double userMoney;

    @Column(name = "user_class", nullable = true, columnDefinition = "VARCHAR(50)")
    private String userClass;

    @Column(name = "user_wrong_password", columnDefinition = "INTEGER")
    private Integer userWrongPassword;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "idcard_id",nullable = false)
    private IdCard idCard;

    @ManyToOne
    @JoinColumn(name = "user_status_id", nullable = false)
    private UserStatus status;

    @OneToMany(mappedBy = "user")
    private Set<OneTimePassword> oneTimePasswords = new HashSet<>();
}
