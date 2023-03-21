package ntnu.idatt2105.project.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
@Schema(description = "A category type that can be assigned to an item")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The id of the category, automatically generated")
    private Long id;

    @Column(name = "type")
    @Schema(description = "The type of the category")
    private String type;
}