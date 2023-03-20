package ntnu.idatt2105.project.backend.model;

import lombok.Getter;
import lombok.Setter;

import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "bookmark")
@Schema(description = "A bookmark table for keeping track of which user has bookmarked what item")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The id of the bookmark, automatically generated")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "The id of the user who has bookmarked the item")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @Schema(description = "The id of the item that was bookmarked")
    private Item item;
}
