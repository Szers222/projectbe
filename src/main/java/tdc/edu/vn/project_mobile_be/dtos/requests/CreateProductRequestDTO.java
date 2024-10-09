package tdc.edu.vn.project_mobile_be.dtos.requests;



import lombok.Data;
import tdc.edu.vn.project_mobile_be.entities.product.Product;
import tdc.edu.vn.project_mobile_be.interfaces.IDto;



@Data
public class CreateProductRequestDTO implements IDto<Product> {

    @Override
    public Product toEntity() {
        return null;
    }

    @Override
    public void toDto(Product entity) {

    }
}
