package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProductId;

import java.util.UUID;

public interface CartProductRepository extends JpaRepository<CartProduct, CartProductId> {

    @Query(value = "SELECT * FROM cart_product WHERE cart_id = ?1", nativeQuery = true)
    CartProduct findByCartId(UUID cartId);
}
