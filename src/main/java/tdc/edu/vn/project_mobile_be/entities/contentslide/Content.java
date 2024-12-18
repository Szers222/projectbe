package tdc.edu.vn.project_mobile_be.entities.contentslide;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "content_slides")


public class Content {
    @Id
    @Column(name = "content_id", nullable = false,columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "content_name", nullable = false)
    private String content;

    @Column(name = "content_status", nullable = false)
    private String status;
}
