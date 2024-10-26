package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;

import java.util.List;
import java.util.UUID;

public interface ProductSizeRepository extends JpaRepository<ProductSize, UUID> {

    @Query("SELECT s FROM ProductSize s WHERE s.productSizeId IN :ids")
    List<ProductSize> findAllById(@Param("ids") List<UUID> ids);

    @Query("SELECT s FROM ProductSize s WHERE s.productSizeType = :type")
    List<ProductSize> findAllByType(@Param("type") int type);

    @Query("SELECT s FROM ProductSize s LEFT JOIN FETCH s.sizeProducts p WHERE p.product.productId IN :productIds")
    List<ProductSize> findAllByProductId(@Param("productIds") List<UUID> productIds);
}
