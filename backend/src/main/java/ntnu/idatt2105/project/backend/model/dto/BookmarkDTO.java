package ntnu.idatt2105.project.backend.model.dto;

import lombok.Getter;
import lombok.Setter;
import ntnu.idatt2105.project.backend.model.Bookmark;

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

    public BookmarkDTO(Bookmark bookmark) {
        this.id = bookmark.getId();
        this.userId = bookmark.getUser().getId();
        this.itemId = bookmark.getItem().getId();
    }
}