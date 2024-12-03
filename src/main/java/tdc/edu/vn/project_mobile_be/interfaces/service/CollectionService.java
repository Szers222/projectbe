package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.dtos.requests.collection.CollectionCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.collection.CollectionResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.collection.Collection;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.UUID;

public interface CollectionService extends IService<Collection, UUID> {
    Collection createSlideshowImage(CollectionCreateRequestDTO params, MultipartFile file);

    CollectionResponseDTO getCollection(UUID id);
}
