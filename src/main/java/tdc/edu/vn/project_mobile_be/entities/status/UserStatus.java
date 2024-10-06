package tdc.edu.vn.project_mobile_be.entities.status;

import jakarta.persistence.*;
import lombok.Data;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "user_status")
public class UserStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_status_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "user_status_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "user_status_type", nullable = false, columnDefinition = "int default 0")
    private String type;

    @OneToMany(mappedBy = "status")
    private Set<User> users = new HashSet<>();

}
