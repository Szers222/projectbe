package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.interfaces.service.DatabaseTriggerService;

@Service
public class DatabaseTriggerServiceImpl implements DatabaseTriggerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createTriggerAndFunction() {
        // Xóa các trigger và procedure cũ nếu tồn tại
        String[] dropStatements = {
                "DROP TRIGGER IF EXISTS product_insert_trigger",
                "DROP TRIGGER IF EXISTS product_update_trigger",
                "DROP PROCEDURE IF EXISTS rabbitmq_send"
        };

        // Tạo stored procedure
        String createProcedure = """
            CREATE PROCEDURE rabbitmq_send(
                IN queue_name VARCHAR(255),
                IN message JSON
            )
            BEGIN
                DECLARE msg_status VARCHAR(100);
                SET msg_status = CONCAT('Message sent to ', queue_name);
                INSERT INTO message_logs (status) VALUES (msg_status);
            END
        """;

        // Tạo trigger cho INSERT
        String createInsertTrigger = """
            CREATE TRIGGER product_insert_trigger
            AFTER INSERT ON products
            FOR EACH ROW
            BEGIN
                CALL rabbitmq_send('product_changes', 
                    JSON_OBJECT(
                        'operation', 'INSERT',
                        'productId', NEW.product_id,
                        'productName', NEW.product_name,
                        'productPrice', NEW.product_price,
                        'productQuantity', NEW.product_quantity,
                        'productYearOfManufacture', NEW.product_year_of_manufacture,
                        'timestamp', NOW()
                    )
                );
            END
        """;

        // Tạo trigger cho UPDATE
        String createUpdateTrigger = """
            CREATE TRIGGER product_update_trigger
            AFTER UPDATE ON products
            FOR EACH ROW
            BEGIN
                CALL rabbitmq_send('product_changes', 
                    JSON_OBJECT(
                        'operation', 'UPDATE',
                        'productId', NEW.product_id,
                        'productName', NEW.product_name,
                        'productPrice', NEW.product_price,
                        'productQuantity', NEW.product_quantity,
                        'productYearOfManufacture', NEW.product_year_of_manufacture,
                        'oldProductName', OLD.product_name,
                        'oldProductPrice', OLD.product_price,
                        'oldProductQuantity', OLD.product_quantity,
                        'oldProductYearOfManufacture', OLD.product_year_of_manufacture,
                        'timestamp', NOW()
                    )
                );
            END
        """;

        try {
            // 1. Xóa các object cũ
            for (String dropStatement : dropStatements) {
                jdbcTemplate.execute(dropStatement);
            }

            // 2. Tạo procedure và triggers mới
            jdbcTemplate.execute(createProcedure);
            jdbcTemplate.execute(createInsertTrigger);
            jdbcTemplate.execute(createUpdateTrigger);

            System.out.println("Đã tạo thành công triggers và procedure");
        } catch (Exception e) {
            System.err.println("Lỗi khi tạo triggers và procedure: " + e.getMessage());
            e.printStackTrace();
        }
    }
}