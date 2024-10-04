package tdc.edu.vn.project_mobile_be.entities.roles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdc.edu.vn.project_mobile_be.entities.permissions.Permission;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permissions_roles")
public class PermissionRole {

    @Id
    @ManyToOne
    @JoinColumn(name = "permissions_id", nullable = false)
    private Permission permission;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}