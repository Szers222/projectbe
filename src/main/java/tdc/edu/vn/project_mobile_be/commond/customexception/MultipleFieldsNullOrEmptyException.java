package tdc.edu.vn.project_mobile_be.commond.customexception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipleFieldsNullOrEmptyException extends RuntimeException {
    private List<String> errorMessages;
}
