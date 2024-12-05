package tdc.edu.vn.project_mobile_be.entities.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import tdc.edu.vn.project_mobile_be.entities.order.Order;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.entities.roles.Role;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "user_email", nullable = false, columnDefinition = "VARCHAR(255)")
    private String userEmail;

    @Column(name = "user_password", nullable = false, columnDefinition = "VARCHAR(255)")
    private String userPassword = "";


    @Column(name = "user_phone", nullable = false, columnDefinition = "VARCHAR(15)")
    private String userPhone = "";

    @Column(name = "user_birthday", columnDefinition = "TIMESTAMP")
    private Timestamp userBirthday;

    @Column(name = "user_image_path", columnDefinition = "VARCHAR(255)")
    private String userImagePath;

    @Column(name = "cancel_count", columnDefinition = "int default 0")
    private int cancelCount;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "user_password_level_2", nullable = true, columnDefinition = "VARCHAR(255)")
    private String userPasswordLevel2;

    @Column(name = "user_last_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String userLastName = "";

    @Column(name = "user_first_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String userFirstName = "";

    @Column(name = "user_money", nullable = false, columnDefinition = "DOUBLE default 0")
    private Double userMoney;

    @Column(name = "user_point", nullable = true, columnDefinition = "int default 0")
    private int userPoint;

    @Column(name = "user_wrong_password", columnDefinition = "int default 0")
    private int userWrongPassword;

    @Column(name = "user_status", columnDefinition = "int default 0")
    private int userStatus;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "card_id", nullable = true)
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private IdCard iCard;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-post")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Post> post;


    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference(value = "user-role")
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserOtp> userOtp = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-cart")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Cart> carts = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserAddress detail;

}