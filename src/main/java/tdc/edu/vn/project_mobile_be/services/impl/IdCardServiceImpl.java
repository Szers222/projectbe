package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateIdCardRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.IdCardResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.IdCardRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.IdCardService;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IdCardServiceImpl implements IdCardService {

    @Autowired
    private IdCardRepository idCardRepository;

    //Create
    @Override
    public IdCardResponseDTO createIdCard(CreateIdCardRequestDTO idCardRequestDTO) {
        IdCard idCard = idCardRequestDTO.toEntity();
        IdCard savedIdCard = idCardRepository.save(idCard);

        IdCardResponseDTO idCardResponseDTO = new IdCardResponseDTO();
        idCardResponseDTO.toDto(savedIdCard);
        return idCardResponseDTO;
    }

    // Get ALl
    @Override
    public List<IdCardResponseDTO> getAllIdCards() {
        return idCardRepository.findAll().stream()
                .map(idCard -> {
                    IdCardResponseDTO responseDTO = new IdCardResponseDTO();
                    responseDTO.toDto(idCard);
                    return responseDTO;
                })
                .collect(Collectors.toList());
    }
    //Delete
    @Override
    public void deleteIdCard(UUID cardId){
        Optional<IdCard> idCardOptional = idCardRepository.findById(cardId);
        if(idCardOptional.isPresent()){
            idCardRepository.delete(idCardOptional.get());
        }
        else {
            throw new ResolutionException("Khong tim thay id " + cardId);
        }
    }
    //Update
    @Override
    public IdCardResponseDTO updateIdCard(UUID cardId, CreateIdCardRequestDTO idCardRequestDTO) {
        IdCard existingIdCard = idCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy thẻ ID với ID: "
                        + cardId));
        // Cap nhat thong tin
        existingIdCard.setIdCardNumber(idCardRequestDTO.getIdCardNumber());
        existingIdCard.setImageFrontPath(idCardRequestDTO.getImageFrontPath());
        existingIdCard.setImageBackPath(idCardRequestDTO.getImageBackPath());
        existingIdCard.setIdCardDate(idCardRequestDTO.getIdCardDate());

        // Luu lai thong tin
        IdCard updateIdCard = idCardRepository.save(existingIdCard);

        //Chuyen doi entity sang DTO
        IdCardResponseDTO responseDTO = new IdCardResponseDTO();
        responseDTO.toDto(updateIdCard);
        return responseDTO;

    }
    //Lay id
    @Override
    public IdCardResponseDTO getIdCardById(UUID cardId){
        IdCard idCard = idCardRepository.findById(cardId).
                orElseThrow(() ->
                        new RuntimeException("Khong tim thay ID " + cardId));
        //Chuyen doi entity sang DTO
        IdCardResponseDTO responseDTO = new IdCardResponseDTO();
        responseDTO.toDto(idCard);
        return responseDTO;

    }

}
