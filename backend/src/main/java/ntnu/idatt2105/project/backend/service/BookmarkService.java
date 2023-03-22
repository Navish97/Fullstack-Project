package ntnu.idatt2105.project.backend.service;

import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.Bookmark;
import ntnu.idatt2105.project.backend.model.dto.BookmarkDTO;
import ntnu.idatt2105.project.backend.repository.BookmarkRepository;
import org.springframework.stereotype.Service;
import ntnu.idatt2105.project.backend.model.User;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public boolean isItemBookmarkedByUser(String userId, Long itemId) {
        return bookmarkRepository.findByUserIdAndItemId(userId, itemId).isPresent();
    }

    public List<BookmarkDTO> getAllBookmarksForUser(Optional<User> user) {
        return bookmarkRepository.findAllBookmarksByUser(user);
    }

    public void removeBookmark(String userId, Long itemId) {
        Optional<Bookmark> bookmark = bookmarkRepository.findByUserIdAndItemId(userId, itemId);
        bookmark.ifPresent(bookmarkRepository::delete);
    }
}
