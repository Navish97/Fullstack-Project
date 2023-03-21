package ntnu.idatt2105.project.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.dto.BookmarkDTO;
import ntnu.idatt2105.project.backend.exceptions.UnauthorizedException;
import ntnu.idatt2105.project.backend.exceptions.UserNotFoundException;
import ntnu.idatt2105.project.backend.model.Bookmark;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.repository.BookmarkRepository;
import ntnu.idatt2105.project.backend.repository.UserRepository;
import ntnu.idatt2105.project.backend.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://mymarketplace-xt5ws57jza-lz.a.run.app"})
public class BookmarkController {

    private final UserRepository userRepository;

    private final BookmarkRepository bookmarkRepository;

    private final JwtService jwtService;

    Logger logger = LoggerFactory.getLogger(BookmarkController.class);

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

        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);
        List<BookmarkDTO> bookmarkDTO = bookmarks.stream()
                .map(bookmark -> new BookmarkDTO(bookmark.getId(), bookmark.getUser().getId(), bookmark.getItem().getId()))
                .collect(Collectors.toList());
        logger.info("User found, returning bookmarks for user with email: " + email);
        return ResponseEntity.ok(bookmarkDTO);
    }
}