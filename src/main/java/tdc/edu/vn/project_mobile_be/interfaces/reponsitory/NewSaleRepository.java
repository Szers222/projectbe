package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.collection.Collection;
import tdc.edu.vn.project_mobile_be.entities.collection.NewSale;

import java.util.UUID;

@Repository
public interface NewSaleRepository extends JpaRepository<NewSale, UUID> {
    @Query("SELECT n FROM NewSale n WHERE n.newSaleStatus = ?1")
    NewSale findByStatus(Integer status);
}
