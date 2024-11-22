package tdc.edu.vn.project_mobile_be.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements Serializable {
    private String type;      // CREATE, UPDATE, DELETE
    private String table;     // Affected table name
    private Object data;      // Changed data
    private LocalDateTime timestamp;

    public static Notification from(String type, String table, Object data) {
        return new Notification(type, table, data, LocalDateTime.now());
    }
}




