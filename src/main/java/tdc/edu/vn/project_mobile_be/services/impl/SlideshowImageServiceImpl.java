package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.dtos.requests.slideshowimage.SlideshowImageCreateDTO;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.entities.slideshow.SlideshowImage;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.SlideshowRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.SlideshowImageService;

import java.util.Set;
import java.util.UUID;

@Service
public class SlideshowImageServiceImpl extends AbService<SlideshowImage, UUID> implements SlideshowImageService {

    @Autowired
    private SlideshowRepository slideshowRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;


    @Override
    public SlideshowImage createSlideshowImage(SlideshowImageCreateDTO params, MultipartFile file) {
        return null;
    }

}
