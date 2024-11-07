package tdc.edu.vn.project_mobile_be.commond.customexception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipleFieldsNullOrEmptyException extends RuntimeException {
    private List<String> errorMessages;
}
