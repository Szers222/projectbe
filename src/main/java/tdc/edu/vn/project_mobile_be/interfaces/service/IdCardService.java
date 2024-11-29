package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.idcard.CreateIdCardRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.idcard.IdCardResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;

import java.util.List;
import java.util.UUID;

public interface IdCardService {
    IdCard uploadIdCardImages(CreateIdCardRequestDTO idCardRequestDTO);
    boolean existsByIdCardNumber(String idCardNumber);
    IdCard updateIdCard(UUID cardId, CreateIdCardRequestDTO idCardRequestDTO);
    void deleteIdCard(UUID cardId);

}
