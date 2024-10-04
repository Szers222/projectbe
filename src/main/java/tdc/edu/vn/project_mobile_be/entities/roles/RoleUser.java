package tdc.edu.vn.project_mobile_be.entities.roles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.entities.user.User;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles_users")
public class RoleUser {

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}