package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.newsale.NewSaleCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.newsale.NewSaleUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.newsale.NewSaleResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.collection.NewSale;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface NewSaleService extends IService<NewSale, UUID> {
    NewSale createSlideshowImage(NewSaleCreateRequestDTO params);

    NewSaleResponseDTO getNewSale(int status);

    void deleteNewSale(UUID id);

    List<NewSaleResponseDTO> getAllNewSale();

    NewSale updateNewSale(UUID id, NewSaleUpdateRequestDTO params);
}
