package tdc.edu.vn.project_mobile_be.dtos.requests.category;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestParamsDTO {
    private int page = 0;
    private int size = 20;
    private String sort = "categoryName";
    private String direction = "asc";
    private String search = "";

}
