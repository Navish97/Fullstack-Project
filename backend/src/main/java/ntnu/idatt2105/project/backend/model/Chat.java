package ntnu.idatt2105.project.backend.model;


import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of a chat, automatically generated")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user-one-id")
    @Schema(description = "the id of user one")
    private User userOne;

    @ManyToOne
    @JoinColumn(name = "user-one-id")
    @Schema(description = "the id of user one")
    private User userTwo;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @Schema(description = "item being discussed in chat")
    private Item item;


}
