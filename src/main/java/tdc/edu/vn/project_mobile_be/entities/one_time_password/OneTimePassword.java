package tdc.edu.vn.project_mobile_be.entities.one_time_password;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name = "one_time_password")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class OneTimePassword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "one_time_password_id", nullable = false,columnDefinition = "BINARY(16)")
    private UUID oneTimePasswordId;

    @Column(name = "one_time_password_code", nullable = false, columnDefinition = "VARCHAR(6)")
    private String oneTimePasswordCode;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "one_time_password_wrong")
    private Integer oneTimePasswordWrong;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "TIMESTAMP")
    private User user;
}