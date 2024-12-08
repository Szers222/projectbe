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
//    @Query("SELECT sp FROM SizeProduct sp WHERE sp.id.product_id = :productIds")
//    List<SizeProduct> findByProductId(UUID productIds);

    @Query("SELECT sp FROM SizeProduct sp inner join sp.size s inner join sp.product p WHERE p.productId = ?1 and s.productSizeId = ?2")
    SizeProduct findBySizeIdAndProductId(UUID productId, UUID sizeId);

    @Query("SELECT sp FROM SizeProduct sp WHERE sp.product.productId = ?1 ")
    List<SizeProduct> findByProductId(UUID productId);



}
