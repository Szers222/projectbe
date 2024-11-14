package tdc.edu.vn.project_mobile_be.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_address")
@Builder
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_address_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID addressId;

    @Column(name = "user_address_name", columnDefinition = "TEXT", nullable = false)
    private String addressName;

    @Column(name = "user_commune", columnDefinition = "VARCHAR(45)", nullable = false)
    private String commune;
    @Column(name = "user_district", columnDefinition = "VARCHAR(45)", nullable = false)
    private String district;
    @Column(name = "user_city",columnDefinition = "VARCHAR(45)", nullable = false)
    private String city;

    @OneToOne(mappedBy = "userAddress", cascade = CascadeType.ALL)
    private User user;
}
