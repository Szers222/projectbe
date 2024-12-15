package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
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

//    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories c WHERE c.categoryId= :categoryId")
//    List<Product> findByCategoryId(@Param("categoryId") UUID categoryId);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.categoryId= :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") UUID categoryId);

    @Query("SELECT p FROM Product p  JOIN p.categories c on  c.categoryId = :categoryId  order by p.productSale desc LIMIT 20")
    List<Product> findByIdWithCategories(@Param("categoryId") UUID categoryId);

    @Query("SELECT p FROM Product p JOIN  p.sizeProducts sp on p.productId =:productId WHERE sp.size.productSizeId = :sizeId")
    Product findBySizeId(@Param("productId") UUID productId,@Param("sizeId") UUID sizeId);

    @Modifying
    @Query("delete from Product p where p.productId = :productId")
    void deleteByProductId(@Param("productId") UUID productId);

    @Query("SELECT p FROM Product p join p.collections pc on pc.collectionId = :collectionId")
    List<Product> findByCollectionId(@Param("collectionId") UUID collectionId);

    @Query("SELECT p FROM Product p join p.newsales pc on pc.newSaleId = ?1")
    List<Product> findByNewSalesStatus(UUID newSaleId);

    @Query(value = "SELECT * FROM products p WHERE p.created_at >= CURRENT_TIMESTAMP - INTERVAL 30 DAY ORDER BY p.created_at DESC LIMIT 10", nativeQuery = true)
    List<Product> findTop10ByOrderByCreatedAtDesc();

    @Query("SELECT p FROM Product p WHERE p.productId =:productId")
    Product findByProductId(@Param("productId") UUID productId);
}
