package tdc.edu.vn.project_mobile_be.commond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Breadcrumb {
    private String text;
    private String url;
}
