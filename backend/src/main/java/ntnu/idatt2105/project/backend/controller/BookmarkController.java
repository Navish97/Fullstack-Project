package ntnu.idatt2105.project.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.dto.BookmarkDTO;
import ntnu.idatt2105.project.backend.exceptions.UserNotFoundException;
import ntnu.idatt2105.project.backend.model.Bookmark;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.repository.BookmarkRepository;
import ntnu.idatt2105.project.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
public class BookmarkController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    Logger logger = LoggerFactory.getLogger(BookmarkController.class);
    @Operation(summary = "Get a user's bookmarks",
            description = "Returns a list of bookmark objects for the user with the given email address.",
            parameters = {
                    @Parameter(name = "email",
                            description = "The email address of the user to retrieve bookmarks for.")
            },
            responses = {
            @ApiResponse(responseCode = "200", description = "The list of bookmarks for the user.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookmarkDTO.class)))
            })
    @GetMapping("/user")
    public ResponseEntity<?> getUserBookmarks(@RequestBody Map<String, String> requestBody) throws UserNotFoundException {
        String email = requestBody.get("email");
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with email: " + email + " not found");
        }
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);
        List<BookmarkDTO> bookmarkDTO = bookmarks.stream()
                .map(bookmark -> new BookmarkDTO(bookmark.getId(), bookmark.getUser().getId(), bookmark.getItem().getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookmarkDTO);
    }
}