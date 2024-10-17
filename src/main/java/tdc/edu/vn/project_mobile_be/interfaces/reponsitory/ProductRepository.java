package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSize;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    List<Product> findAll();

    Optional<Product> findById(UUID id);

    Page<Product> findAll(Pageable pageable);

    Product save(Product product);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images i WHERE p.productId = :productId ORDER BY i.productImageIndex ASC")
    Optional<Product> findByIdWithImages(@Param("productId") UUID productId);


    @Query("SELECT p.sizes FROM Product p JOIN p.categories c WHERE c.categoryId = :categoryId")
    List<ProductSize> findProductSizesByCategory(@Param("categoryId") UUID categoryId);

    @Query("SELECT DISTINCT p.supplier FROM Product p JOIN p.categories c WHERE c.categoryId = :categoryId")
    List<ProductSupplier> findProductSuplierByCategory(@Param("categoryId") UUID categoryId);
}
