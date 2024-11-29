package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface IdCardRepository extends JpaRepository<IdCard, UUID> {

    Optional<IdCard> findByCardId(UUID cardId);
    boolean existsByIdCardNumber(String idCardNumber);
}
