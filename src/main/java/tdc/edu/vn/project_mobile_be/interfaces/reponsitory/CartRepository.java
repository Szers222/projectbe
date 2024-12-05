package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    @Query("SELECT c FROM Cart c join c.user cu on cu.userId= ?1 AND c.cartStatus = ?2")
    List<Cart> findByUserId(UUID userId, int cartStatus);

    @Query("SELECT c FROM Cart c WHERE c.guestId = ?1")
    Optional<Cart> findByGuestId(UUID guestId);

    @Query("SELECT c FROM Cart c inner join  c.user cu on cu.userId = ?1 where c.cartStatus = ?2")
    List<Cart> findByUserIdAndCartStatus(UUID userId, int cartStatus);
}
