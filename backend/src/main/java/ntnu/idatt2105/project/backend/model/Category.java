package ntnu.idatt2105.project.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
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

    @Column(name = "icon_url")
    @Schema(description = "The URL to the icon of the category")
    private String icon_url;
}