package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    List<Product> findAll();

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images i WHERE p.productId= :productId ORDER BY i.productImageIndex desc")
    Optional<Product> findByIdWithImages(@Param("productId") UUID productId);

    @Query("SELECT DISTINCT p FROM Product p JOIN FETCH p.categories c WHERE c.categoryId= :categoryId")
    Page<Product> findByCategoryId(@Param("categoryId") UUID categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.categoryId = :categoryId")
    Page<Product> findByIdWithCategories(@Param("categoryId") UUID categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN  p.sizeProducts sp on p.productId =:productId WHERE sp.size.productSizeId = :sizeId")
    Product findBySizeId(@Param("productId") UUID productId,@Param("sizeId") UUID sizeId);



}
