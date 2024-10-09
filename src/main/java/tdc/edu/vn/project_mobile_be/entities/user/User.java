package tdc.edu.vn.project_mobile_be.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import tdc.edu.vn.project_mobile_be.entities.one_time_password.OneTimePassword;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.entities.roles.Role;
import tdc.edu.vn.project_mobile_be.entities.status.UserStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "user_email", nullable = false, columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(name = "user_password", nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;


    @Column(name = "user_phone", nullable = false, columnDefinition = "VARCHAR(15)")
    private String phone;

    @Column(name = "user_birthday", columnDefinition = "TIMESTAMP")
    private Timestamp birthday;

    @Column(name = "user_address", columnDefinition = "TEXT", nullable = true)
    private String address;

    @Column(name = "user_image_path", columnDefinition = "VARCHAR(255)")
    private String imagePath;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "user_password_level_2", nullable = true, columnDefinition = "VARCHAR(255)")
    private String passwordLevel2;

    @Column(name = "user_last_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String lastName;

    @Column(name = "user_first_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String firstName;

    @Column(name = "user_money", nullable = false, columnDefinition = "DOUBLE default 0")
    private Double money;

    @Column(name = "user_point", nullable = true, columnDefinition = "int default 0")
    private int point;

    @Column(name = "user_wrong_password", columnDefinition = "int default 0")
    private Integer wrongPassword;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "icard_id", nullable = false)
    private IdCard iCard;

    @OneToOne(mappedBy = "user")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_status_id", nullable = false)
    private UserStatus status;

    @OneToMany(mappedBy = "user")
    private Set<OneTimePassword> oneTimePasswords = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();
}
