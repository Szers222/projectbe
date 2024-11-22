package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.idcard.CreateIdCardRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.idcard.IdCardResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IdCardService {
    IdCardResponseDTO createIdCard(CreateIdCardRequestDTO idCardRequestDTO);
    List<IdCardResponseDTO> getAllIdCards();
    void deleteIdCard(UUID cardId);
    IdCardResponseDTO updateIdCard(UUID cardId, CreateIdCardRequestDTO idCardRequestDTO);
    //IdCardResponseDTO getIdCardById(UUID cardId);

}
