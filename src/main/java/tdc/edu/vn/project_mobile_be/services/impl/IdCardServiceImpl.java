package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.idcard.CreateIdCardRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.IdCardRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.IdCardService;

import java.io.IOException;

import java.util.UUID;


@Service
public class IdCardServiceImpl implements IdCardService {
    @Autowired
    private IdCardRepository idCardRepository;

    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;

    @Override
    public IdCard uploadIdCardImages(CreateIdCardRequestDTO idCardRequestDTO) {
        // Kiểm tra nếu idCardNumber đã tồn tại
        if (idCardRepository.existsByIdCardNumber(idCardRequestDTO.getIdCardNumber())) {
            throw new IllegalArgumentException("IdCardNumber đã tồn tại: " + idCardRequestDTO.getIdCardNumber());
        }

        try {
            // Tạo một thực thể IdCard mới
            IdCard idCard = new IdCard();
            idCard.setCardId(UUID.randomUUID());
            idCard.setIdCardNumber(idCardRequestDTO.getIdCardNumber());
            idCard.setIdCardDate(idCardRequestDTO.getIdCardDate());
            // Upload ảnh mặt trước
            if (idCardRequestDTO.getImageFront() != null && !idCardRequestDTO.getImageFront().isEmpty()) {
                String frontUrl = googleCloudStorageService.uploadFile(idCardRequestDTO.getImageFront());
                idCard.setImageFrontPath(frontUrl);
            }
            // Upload ảnh mặt sau
            if (idCardRequestDTO.getImageBack() != null && !idCardRequestDTO.getImageBack().isEmpty()) {
                String backUrl = googleCloudStorageService.uploadFile(idCardRequestDTO.getImageBack());
                idCard.setImageBackPath(backUrl);
            }
            // Lưu IdCard vào cơ sở dữ liệu
            return idCardRepository.save(idCard);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload ảnh: " + e.getMessage());
        }
    }
    //Update
    @Override
    public IdCard updateIdCard(UUID cardId, CreateIdCardRequestDTO idCardRequestDTO) {
        // Tìm IdCard cần cập nhật
        IdCard idCard = idCardRepository.findByCardId(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy IdCard với ID: " + cardId));

        try {
            // Xử lý ảnh mặt trước
            if (idCardRequestDTO.getImageFront() != null && !idCardRequestDTO.getImageFront().isEmpty()) {
                // Xóa ảnh cũ trên Google Cloud Storage nếu có
                if (idCard.getImageFrontPath() != null) {
                    googleCloudStorageService.deleteFile(idCard.getImageFrontPath());
                }
                // Upload ảnh mới và cập nhật đường dẫn
                String frontUrl = googleCloudStorageService.uploadFile(idCardRequestDTO.getImageFront());
                idCard.setImageFrontPath(frontUrl);
            }

            // Xử lý ảnh mặt sau
            if (idCardRequestDTO.getImageBack() != null && !idCardRequestDTO.getImageBack().isEmpty()) {
                // Xóa ảnh cũ trên Google Cloud Storage nếu có
                if (idCard.getImageBackPath() != null) {
                    googleCloudStorageService.deleteFile(idCard.getImageBackPath());
                }
                // Upload ảnh mới và cập nhật đường dẫn
                String backUrl = googleCloudStorageService.uploadFile(idCardRequestDTO.getImageBack());
                idCard.setImageBackPath(backUrl);
            }

            // Cập nhật thông tin khác
            idCard.setIdCardNumber(idCardRequestDTO.getIdCardNumber());
            idCard.setIdCardDate(idCardRequestDTO.getIdCardDate());

            // Lưu thay đổi vào cơ sở dữ liệu
            return idCardRepository.save(idCard);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload ảnh: " + e.getMessage());
        }
    }
    @Override
    public void deleteIdCard(UUID cardId) {
        // Tìm IdCard cần xóa
        IdCard idCard = idCardRepository.findByCardId(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy IdCard với ID: " + cardId));

        // Xóa ảnh trên Google Cloud Storage
        if (idCard.getImageFrontPath() != null) {
            googleCloudStorageService.deleteFile(idCard.getImageFrontPath());
        }
        if (idCard.getImageBackPath() != null) {
            googleCloudStorageService.deleteFile(idCard.getImageBackPath());
        }

        // Xóa IdCard trong cơ sở dữ liệu
        idCardRepository.delete(idCard);
    }



    @Override
    public boolean existsByIdCardNumber(String idCardNumber) {
        return idCardRepository.existsByIdCardNumber(idCardNumber);
    }
}


