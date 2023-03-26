package ntnu.idatt2105.project.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "item_images")
@Schema(description = "An image associated with an item")
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The id of the item image, automatically generated")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Lob
    @Column(name = "data")
    @Schema(description = "The binary data of the image")
    private byte[] data;

    @Column(name = "content_type")
    @Schema(description = "The content type of the image")
    private String contentType;
}