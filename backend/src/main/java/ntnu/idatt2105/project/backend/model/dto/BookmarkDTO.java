package ntnu.idatt2105.project.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkDTO {
    private Long id;
    private String userId;
    private Long itemId;

    public BookmarkDTO(Long id, String userId, Long itemId) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
    }
}