package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;

import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    @Query(value = "SELECT * FROM cart WHERE user_id = ?1", nativeQuery = true)
    Cart findByUserId(UUID userId);
}
