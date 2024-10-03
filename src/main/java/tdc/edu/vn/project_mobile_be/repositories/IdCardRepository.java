package tdc.edu.vn.project_mobile_be.repositories;

import tdc.edu.vn.project_mobile_be.entities.idcards.IdCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdCardRepository extends JpaRepository<IdCard, String> {
}
