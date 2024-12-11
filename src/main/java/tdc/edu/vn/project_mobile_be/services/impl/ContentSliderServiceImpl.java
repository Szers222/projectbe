package tdc.edu.vn.project_mobile_be.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.ListNotFoundException;
import tdc.edu.vn.project_mobile_be.dtos.requests.contentslide.ContentSlideCreateDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.contentslide.ContentSlideUpdateDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.contentslide.ContentSlideResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.contentslide.Content;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ContentSlideRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.ContentSlideService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContentSliderServiceImpl extends AbService<Content, UUID> implements ContentSlideService {

    @Autowired
    private ContentSlideRepository contentSlideRepository;

    @Override
    public Content createContentSlide(ContentSlideCreateDTO params) {
        Content contentSlide = params.toEntity();
        contentSlide.setId(UUID.randomUUID());
        return contentSlideRepository.save(contentSlide);
    }

    @Override
    public Content updateContentSlide(ContentSlideUpdateDTO params, UUID id) {
        Content contentSlide = contentSlideRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ContentSlide not found"));

        contentSlide.setContent(params.getContent());
        contentSlide.setStatus(params.getStatus());
        return contentSlideRepository.save(contentSlide);
    }

    @Override
    public void deleteContentSlide(UUID id) {
        Content contentSlide = contentSlideRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ContentSlide not found"));
        contentSlideRepository.delete(contentSlide);
    }

    @Override
    public List<ContentSlideResponseDTO> getAllContentSlides() {
        List<ContentSlideResponseDTO> contentSlideResponseDTOList = new ArrayList<>();

        contentSlideRepository.findAll().forEach(contentSlide -> {
            ContentSlideResponseDTO contentSlideResponseDTO = new ContentSlideResponseDTO();
            contentSlideResponseDTO.toDto(contentSlide);
            contentSlideResponseDTO.setId(contentSlide.getId());
            contentSlideResponseDTO.setContent(contentSlide.getContent());
            contentSlideResponseDTO.setStatus(contentSlide.getStatus());

            contentSlideResponseDTOList.add(contentSlideResponseDTO);
        });

        return contentSlideResponseDTOList;
    }

}

