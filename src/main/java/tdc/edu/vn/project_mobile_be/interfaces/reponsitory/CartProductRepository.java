package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProduct;
import tdc.edu.vn.project_mobile_be.entities.relationship.CartProductId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartProductRepository extends JpaRepository<CartProduct, CartProductId> {

    @Query("SELECT cp FROM CartProduct cp WHERE cp.cart.cartId = ?1")
    List<CartProduct> findByCartId(UUID cartId);

    @Query("SELECT cp FROM CartProduct cp WHERE cp.cart.cartId = ?1 AND cp.product.productId = ?2 AND cp.productSize.productSizeId = ?3")
    Optional<CartProduct> findByCartIdAndSizeProductId(UUID cartId, UUID productId, UUID sizeId);


}
