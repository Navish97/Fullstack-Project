package ntnu.idatt2105.project.backend.service;

import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.exceptions.BookmarkAlreadyExistsException;
import ntnu.idatt2105.project.backend.model.Bookmark;
import ntnu.idatt2105.project.backend.model.Item;
import ntnu.idatt2105.project.backend.model.dto.BookmarkDTO;
import ntnu.idatt2105.project.backend.model.dto.ItemDTO;
import ntnu.idatt2105.project.backend.repository.BookmarkRepository;
import ntnu.idatt2105.project.backend.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ntnu.idatt2105.project.backend.model.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ItemRepository itemRepository;

    public boolean isItemBookmarkedByUser(String userId, Long itemId) {
        return bookmarkRepository.findByUserIdAndItemId(userId, itemId).isPresent();
    }

    public List<BookmarkDTO> getAllBookmarksForUser(User user) {
        return bookmarkRepository.findAllBookmarkDTOsByUserId(user.getId());
    }

    public Page<ItemDTO> getBookmarkedItemsPage(int pageNr, int pageSize, User user) {
        Page<Bookmark> bookmarks = bookmarkRepository.findBookmarkPageByUserId(user.getId(), PageRequest.of(pageNr, pageSize));
        Page<ItemDTO> items = bookmarks.map(bookmark -> new ItemDTO(bookmark.getItem()));
        return items;
    }

    public void addBookmark(String userId, Long itemId) throws BookmarkAlreadyExistsException {
        if (bookmarkRepository.findByUserIdAndItemId(userId, itemId).isPresent()) {
            throw new BookmarkAlreadyExistsException("Bookmark already exists for user " + userId + " and item " + itemId);
        }
        Bookmark bookmark = new Bookmark();
        User user = new User();
        user.setId(userId);
        Item item = new Item();
        item.setId(itemId);

        bookmark.setUser(user);
        bookmark.setItem(item);
        bookmarkRepository.save(bookmark);
    }

    public void removeBookmark(String userId, Long itemId) {
        Optional<Bookmark> bookmark = bookmarkRepository.findByUserIdAndItemId(userId, itemId);
        bookmark.ifPresent(bookmarkRepository::delete);
    }
}
