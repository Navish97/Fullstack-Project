package ntnu.idatt2105.project.backend.model.dto;

import lombok.Getter;
import lombok.Setter;
import ntnu.idatt2105.project.backend.model.Bookmark;
/**
 * Data transfer object for the Bookmark class. Returns only the userId and itemId instead of entire item.
 */
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