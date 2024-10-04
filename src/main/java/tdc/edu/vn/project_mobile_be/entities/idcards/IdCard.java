package tdc.edu.vn.project_mobile_be.entities.idcards;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tdc.edu.vn.project_mobile_be.entities.user.User;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name = "idcards")
public class IdCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ididcard_id", length = 36, nullable = false)
    private UUID idCardId;

    @Column(name = "idcard_number", length = 15, nullable = false)
    private String idCardNumber;

    @Column(name = "idcard_image_front_path", length = 255)
    private String imageFrontPath;

    @Column(name = "idcard_image_back_path", length = 255)
    private String imageBackPath;

    @Column(name = "idcard_date", length = 10)
    private String idCardDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @OneToOne(mappedBy = "idCard")
    private User user;
}
