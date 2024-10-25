package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;

import java.util.UUID;


@Repository
public interface IdCardRepository extends JpaRepository<IdCard, UUID> {
}
