package tdc.edu.vn.project_mobile_be.entities.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_otps")
public class UserOtp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "otp_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID otpId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "otp" ,nullable = false, columnDefinition = "VARCHAR(15)")
    private String otp;
    @Column(name = "otp_time",nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime otpTime;
}
