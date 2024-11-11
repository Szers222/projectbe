package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tdc.edu.vn.project_mobile_be.entities.product.ProductSupplier;

import java.util.List;
import java.util.UUID;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, UUID> {


    @Query("SELECT DISTINCT p.supplier FROM Product p JOIN p.categories c WHERE c.categoryId = :categoryId")
    List<ProductSupplier> findProductSupplierByCategory(@Param("categoryId") UUID categoryId);
    @Query("SELECT psl from ProductSupplier psl where psl.productSupplierId = :productSupplierId")
    ProductSupplier findProductSupplierById(@Param("productSupplierId") UUID productSupplierId);
}
