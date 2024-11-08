package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.product.ProductImage;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {
    List<ProductImage> findByProductImageId(UUID productId);


    @Query("SELECT p FROM ProductImage p WHERE p.product.productId = :productId")
    Set<ProductImage> findByProductId(@Param("productId") UUID productId);

    @Modifying
    @Query("delete from ProductImage p where p.product.productId = :productId")
    void deleteByProductId(@Param("productId") UUID productId);

}
