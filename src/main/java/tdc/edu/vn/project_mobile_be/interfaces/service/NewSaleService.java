package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.dtos.requests.newsale.NewSaleCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.newsale.NewSaleResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.collection.NewSale;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface NewSaleService extends IService<NewSale, UUID> {
    NewSale createSlideshowImage(NewSaleCreateRequestDTO params, MultipartFile file);

    NewSaleResponseDTO getNewSale(int status);

    void deleteNewSale(UUID id);

    List<NewSaleResponseDTO> getAllNewSale();
}
