package ntnu.idatt2105.project.backend;

import ntnu.idatt2105.project.backend.exceptions.BookmarkAlreadyExistsException;
import ntnu.idatt2105.project.backend.exceptions.UnauthorizedException;
import ntnu.idatt2105.project.backend.exceptions.UserNotFoundException;
import ntnu.idatt2105.project.backend.model.Bookmark;
import ntnu.idatt2105.project.backend.model.Item;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.model.dto.BookmarkDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import ntnu.idatt2105.project.backend.controller.BookmarkController;
import ntnu.idatt2105.project.backend.repository.UserRepository;
import ntnu.idatt2105.project.backend.service.BookmarkService;
import ntnu.idatt2105.project.backend.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookmarkControllerTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BookmarkService bookmarkService;

    @Mock
    private JwtService jwtService;

    private BookmarkController bookmarkController;

    @BeforeEach
    void setUp() {
        bookmarkController = new BookmarkController(userRepository, bookmarkService, jwtService);
    }

    @Test
    void testAddBookmark() throws UserNotFoundException, BookmarkAlreadyExistsException {
        Long itemId = 1L;
        String jwtToken = "jwtToken";
        String userEmail = "user@example.com";
        User user = new User();
        user.setId("Test");
        user.setEmail(userEmail);

        when(jwtService.extractUsername(jwtToken)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(bookmarkService.isItemBookmarkedByUser(user.getId(), itemId)).thenReturn(false);

        ResponseEntity<String> response = bookmarkController.addBookmark(itemId, jwtToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Bookmark successfully added", response.getBody());
        verify(bookmarkService).addBookmark(user.getId(), itemId);
    }

    @Test
    void testRemoveBookmark() throws UserNotFoundException {
        Long itemId = 1L;
        String jwtToken = "jwtToken";
        String userEmail = "user@example.com";
        User user = new User();
        user.setId("Test");
        user.setEmail(userEmail);

        when(jwtService.extractUsername(jwtToken)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(bookmarkService.isItemBookmarkedByUser(user.getId(), itemId)).thenReturn(true);

        ResponseEntity<String> response = bookmarkController.removeBookmark(itemId, jwtToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Bookmark successfully removed", response.getBody());
        verify(bookmarkService).removeBookmark(user.getId(), itemId);
    }

    @Test
    void testRemoveBookmarkNotFound() throws UserNotFoundException {
        Long itemId = 1L;
        String jwtToken = "jwtToken";
        String userEmail = "user@example.com";
        User user = new User();
        user.setId("Test");
        user.setEmail(userEmail);

        when(jwtService.extractUsername(jwtToken)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(bookmarkService.isItemBookmarkedByUser(user.getId(), itemId)).thenReturn(false);

        ResponseEntity<String> response = bookmarkController.removeBookmark(itemId, jwtToken);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Bookmark not found", response.getBody());
        verify(bookmarkService, never()).removeBookmark(user.getId(), itemId);
    }

    @Test
    void testGetUserBookmarks() throws UserNotFoundException, UnauthorizedException {
        String jwtToken = "jwtToken";
        String userEmail = "user@example.com";
        User user = new User();
        user.setId("Test");
        user.setEmail(userEmail);

        Item item = new Item();
        item.setId(1L);

        List<Bookmark> bookmarkList = new ArrayList<>();
        Bookmark bookmark = new Bookmark();
        bookmark.setId(1L);
        bookmark.setItem(item);
        bookmark.setUser(user);
        bookmarkList.add(bookmark);
        List<BookmarkDTO> bookmarkDTOList = bookmarkList.stream().map(BookmarkDTO::new).collect(Collectors.toList());

        when(jwtService.extractUsername(jwtToken)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(bookmarkService.getAllBookmarksForUser(user)).thenReturn(bookmarkDTOList);

        ResponseEntity<?> response = bookmarkController.getUserBookmarks(jwtToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookmarkDTOList, response.getBody());
    }
}