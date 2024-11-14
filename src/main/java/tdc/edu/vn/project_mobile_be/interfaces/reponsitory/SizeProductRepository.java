package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.SizeProductId;

import java.util.List;
import java.util.UUID;

@Repository
public interface SizeProductRepository extends JpaRepository<SizeProduct, SizeProductId> {
    @Query("SELECT sp FROM SizeProduct sp WHERE sp.id.product_id = :productIds")
    List<SizeProduct> findByProductId(UUID productIds);

    @Query("SELECT sp FROM SizeProduct sp WHERE sp.id.product_id = ?1 AND sp.id.product_size_id = ?2")
    SizeProduct findBySizeIdAndProductId(UUID sizeId, UUID productId);

}
