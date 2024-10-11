package tdc.edu.vn.project_mobile_be.entities.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_sizes")
@DynamicInsert
@DynamicUpdate
//@EqualsAndHashCode(exclude = "products")
public class ProductSize {

    @Id
    @Column(name = "product_size_id", nullable = false, columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "product_size_name", nullable = false)
    private String name;
    @Column(name = "product_size_type", nullable = false, columnDefinition = "int default 0")
    private int type;

    @ManyToMany(mappedBy = "sizes")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Product> products = new HashSet<>();
}
