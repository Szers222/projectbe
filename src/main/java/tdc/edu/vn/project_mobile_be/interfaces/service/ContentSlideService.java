package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.contentslide.ContentSlideCreateDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.contentslide.ContentSlideUpdateDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.contentslide.ContentSlideResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.contentslide.Content;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.util.List;
import java.util.UUID;

public interface ContentSlideService extends IService<Content, UUID> {

    Content createContentSlide(ContentSlideCreateDTO params);
    Content updateContentSlide(ContentSlideUpdateDTO params, UUID id);
    void deleteContentSlide(UUID id);

    List<ContentSlideResponseDTO> getAllContentSlides();
}
