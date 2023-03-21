package ntnu.idatt2105.project.backend.model;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
@Schema(description = "Represents a message that is sent between two users")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The id of the message, automatically generated")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @Schema(description = "The id of the user who sent the message")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @Schema(description = "The id of the user who received the message")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @Schema(description = "The id of the item of interest")
    private Item item;

    @Column(name = "message")
    @Schema(description = "The text that is sent in the message")
    private String message;
}
