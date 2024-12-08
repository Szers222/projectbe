package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tdc.edu.vn.project_mobile_be.entities.order.Order;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o join fetch o.cart c join fetch c.user u where u.userId = ?1")
    List<Order> findOrderByUserId(UUID userId);

    @Query("SELECT o FROM Order o join fetch o.cart c where c.cartId = ?1")
    Order findOrderByCartId(UUID cartId);

    @Query("SELECT o FROM Order o where  o.user.userId = ?1")
    List<Order> findOrderByShipperId(UUID shipperId);

    @Query("SELECT o FROM Order o where o.orderStatus = ?1")
    List<Order> findOrderByStatus(int status);

    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startOfDay AND :endOfDay")
    List<Order> findOrdersByDay(@Param("startOfDay") Timestamp startOfDay, @Param("endOfDay") Timestamp endOfDay);

    @Query("SELECT SUM(sp.price * sp.quantity) FROM ShipmentProduct sp JOIN sp.shipment s WHERE s.shipmentDate BETWEEN :startOfDay AND :endOfDay")
    Double findTotalCogsByDay(@Param("startOfDay") Timestamp startOfDay, @Param("endOfDay") Timestamp endOfDay);
}
