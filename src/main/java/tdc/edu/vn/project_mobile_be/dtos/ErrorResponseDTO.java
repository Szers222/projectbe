package tdc.edu.vn.project_mobile_be.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponseDTO {
    private String error;
    private List<String> message = new ArrayList<String>();
}
