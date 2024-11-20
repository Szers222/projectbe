package tdc.edu.vn.project_mobile_be.entities.product;

import org.springframework.context.ApplicationEvent;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductResponseDTO;


public class ProductListeners extends ApplicationEvent {

    private ProductResponseDTO product;

    public ProductListeners(Object source, ProductResponseDTO product) {
        super(source);
        this.product = product;
    }

    public ProductResponseDTO getProduct() {
        return product;
    }
}