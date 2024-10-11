package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {
    List<ProductImage> findByProduct_Id(UUID productId);
}
