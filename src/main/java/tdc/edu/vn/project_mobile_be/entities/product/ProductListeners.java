package tdc.edu.vn.project_mobile_be.entities.product;

import org.springframework.context.ApplicationEvent;


public class ProductListeners extends ApplicationEvent {

    private Product product;

    public ProductListeners(Object source, Product product) {
        super(source);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
