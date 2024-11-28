package tdc.edu.vn.project_mobile_be.entities.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(name = "user_address", columnDefinition = "TEXT", nullable = false)
    private String addressName;

    @Column(name = "user_ward", columnDefinition = "VARCHAR(45)", nullable = false)
    private String ward;
    @Column(name = "user_district", columnDefinition = "VARCHAR(45)", nullable = false)
    private String district;
    @Column(name = "user_city",columnDefinition = "VARCHAR(45)", nullable = false)
    private String city;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

}