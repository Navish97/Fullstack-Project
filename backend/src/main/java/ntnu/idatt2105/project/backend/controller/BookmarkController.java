package ntnu.idatt2105.project.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.exceptions.BookmarkAlreadyExistsException;
import ntnu.idatt2105.project.backend.model.dto.BookmarkDTO;
import ntnu.idatt2105.project.backend.exceptions.UnauthorizedException;
import ntnu.idatt2105.project.backend.exceptions.UserNotFoundException;
import ntnu.idatt2105.project.backend.model.Bookmark;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.repository.BookmarkRepository;
import ntnu.idatt2105.project.backend.repository.UserRepository;
import ntnu.idatt2105.project.backend.service.BookmarkService;
import ntnu.idatt2105.project.backend.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final UserRepository userRepository;

    private final BookmarkService bookmarkService;

    private final JwtService jwtService;

    Logger logger = LoggerFactory.getLogger(BookmarkController.class);

    /**
     * Removes a user's bookmark for a specific item, checks if the user is authenticated first.
     *
     * @param itemId    The ID of the item to remove the bookmark for.
     * @param jwtToken  JWT token containing the user's information.
     * @return ResponseEntity containing a message about the result of the operation.
     * @throws UserNotFoundException if the user is not found.
     */
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{itemId}")
    @Operation(summary = "Removes a user's bookmark for a specific item",
            description = "Removes the bookmark for the given item for the authenticated user." +
                    " The user is not passed as a parameter, but the username is extracted from the JWT token." +
                    " This ensures that users only have access to delete only their own data.",
            parameters = {
                    @Parameter(name = "itemId",
                            description = "The ID of the item to remove the bookmark for.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bookmark successfully removed"),
                    @ApiResponse(responseCode = "404", description = "Bookmark not found")
            })
    public ResponseEntity<String> removeBookmark(@PathVariable Long itemId, @CookieValue(value = "myMarketPlaceAccessToken") String jwtToken) throws UserNotFoundException {
        logger.info("Received remove bookmark request for item with id: " + itemId + " jwtToken: " + jwtToken);

        User user = userRepository.findByEmail(jwtService.extractUsername(jwtToken))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (bookmarkService.isItemBookmarkedByUser(user.getId(), itemId)) {
            logger.info("Bookmark found, removing bookmark for item with id: " + itemId);
            bookmarkService.removeBookmark(user.getId(), itemId);
            return ResponseEntity.ok("Bookmark successfully removed");
        } else {
            logger.info("Bookmark not found, returning 404 (Not Found)");
            return new ResponseEntity<>("Bookmark not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Adds a bookmark for a specific item to a user's bookmarks.
     *
     * @param itemId    The ID of the item to add the bookmark for.
     * @param jwtToken  JWT token containing the user's information.
     * @return ResponseEntity containing a message about the result of the operation.
     * @throws UserNotFoundException if the user is not found.
     * @throws BookmarkAlreadyExistsException if the bookmark already exists for the user.
     */
    @Operation(summary = "Add a bookmark for a specific item to a user's bookmarks",
            description = "Adds a bookmark for the given item to the authenticated user's bookmarks." +
                    " The user is not passed as a parameter, but the username is extracted from the JWT token." +
                    " This ensures that users can only add bookmarks to their own data.",
            parameters = {
                    @Parameter(name = "itemId",
                            description = "The ID of the item to add the bookmark for.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bookmark successfully added"),
                    @ApiResponse(responseCode = "409", description = "Bookmark already exists")
            })
    @PostMapping("/add/{itemId}")
    public ResponseEntity<String> addBookmark(@PathVariable Long itemId, @CookieValue(value = "myMarketPlaceAccessToken") String jwtToken) throws UserNotFoundException, BookmarkAlreadyExistsException {
        logger.info("Received add bookmark request for item with id: " + itemId + " jwtToken: " + jwtToken);

        User user = userRepository.findByEmail(jwtService.extractUsername(jwtToken))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (bookmarkService.isItemBookmarkedByUser(user.getId(), itemId)) {
            logger.info("Bookmark already exists, returning 409 (Conflict)");
            return new ResponseEntity<>("Bookmark already exists", HttpStatus.CONFLICT);
        } else {
            logger.info("Bookmark not found, adding bookmark for item with id: " + itemId);
            bookmarkService.addBookmark(user.getId(), itemId);
            return ResponseEntity.ok("Bookmark successfully added");
        }
    }


    /**
     * Returns a list of bookmark objects for the user with the given email address.
     *
     * @param jwtToken  JWT token containing the user's information.
     * @return ResponseEntity containing a list of bookmark objects for the user.
     * @throws UserNotFoundException if the user is not found.
     * @throws UnauthorizedException if the user is not authorized.
     */
    @PreAuthorize("isAuthenticated()")
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
    public ResponseEntity<?> getUserBookmarks(@CookieValue(value = "myMarketPlaceAccessToken") String jwtToken) throws UserNotFoundException, UnauthorizedException {
        logger.info("Received get bookmark request");
        String email = jwtService.extractUsername(jwtToken);
        logger.info("Getting bookmarks for user with email: " + email + "jwtToken: " + jwtToken);

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            logger.info("User with email: " + email + " not found");
            throw new UserNotFoundException("User with email: " + email + " not found");
        }

        List<BookmarkDTO> bookmarks = bookmarkService.getAllBookmarksForUser(user.get());
        logger.info("User found, returning bookmarks for user with email: " + email);
        return ResponseEntity.ok(bookmarks);
    }
}