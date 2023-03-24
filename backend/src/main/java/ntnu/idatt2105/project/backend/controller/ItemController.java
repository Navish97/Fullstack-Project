package ntnu.idatt2105.project.backend.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.Item;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.model.authentication.RegisterRequest;
import ntnu.idatt2105.project.backend.model.dto.ItemDTO;
import ntnu.idatt2105.project.backend.model.dto.Filter;
import ntnu.idatt2105.project.backend.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.management.PlatformLoggingMXBean;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the Item part of the backend. Used to retrieve filtered items in pages from the database.
 * Handles all requests under the /api/items endpoint.
 */
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "Item Controller", description = "Controller to handle items")
public class ItemController {
    Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final JwtService jwtService;
    private final BookmarkService bookmarkService;
    private final UserService userService;
    private final ItemService itemService;
    private final CookieService cookieService;

    @GetMapping("/details/{itemId}")
    @Operation(summary = "Get item details",
            description = "This retrieves item details by itemId. If the user is logged in, the response will also include whether the item is bookmarked by the user. If the user is unauthenticated, it defaults to false",
            parameters = {
                    @Parameter(name = "itemId", description = "Item ID", required = true),
                    @Parameter(name = "myMarketPlaceAccessToken", description = "JWT Access Token (from myMarketPlaceAccessToken cookie)", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns the ItemDTO with an additional isBookmarked field.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ItemDTO.class)))
            })
    public ResponseEntity<?> getItemDetails(@PathVariable Long itemId, @CookieValue(value = "myMarketPlaceAccessToken", required = false) String jwtToken) throws JsonProcessingException {
        logger.info("Received get item details request");
        ItemDTO item = itemService.getItemById(itemId);
        Map<String, Object> response = new HashMap<>();
        response.put("item", item);

        if (jwtToken != null) {
            User user = userService.findByEmail(jwtService.extractUsername(jwtToken));
            boolean isBookmarked = bookmarkService.isItemBookmarkedByUser(user.getId() , itemId);
            response.put("isBookmarked", isBookmarked);
        } else {
            response.put("isBookmarked", false);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Generates a response containing information about the page being returned. It maps them to a hashmap.
     * @param page
     * @return response containing information about the page being returned.
     */
    private Map<String, Object> generateResponse(final Page<ItemDTO> page){
        Map<String, Object> response = new HashMap<>();
        response.put("items", page.getContent());
        response.put("current-page", page.getNumber());
        response.put("total-items", page.getTotalElements());
        response.put("total-pages", page.getTotalPages());
        return response;
    }

    /**
     * Getmapping that receives a getrequest on the /api/items/page endpoint, and calls on the ItemService to retrieve a page.
     * The method calls the private method parseFilter to parse the JSON String-formatted filter into a filter-object.
     * @param pageNumber
     * @param size
     * @param filter
     * @return response to user containing the page of items.
     * @throws JsonProcessingException
     */
    @Operation(summary = "Retrieve a page of items filtered according to provided filter. ", description = "Retrieves a page of items based on the given page number, page size, and filter. The filter should be provided as a JSON string.")
    @ApiResponse(responseCode = "200", description = "Page of items retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))
    @GetMapping("/page")
    public ResponseEntity<?> getPersonsPageable(
            @RequestParam final Integer pageNumber,
            @RequestParam final Integer size,
            @RequestParam String filter) throws JsonProcessingException {
                logger.info("Received api call for retrieving a page of items. Page: " + pageNumber + " Page size: " + size + " with filter: " + filter);
                Filter f = this.parseFilter(filter);

        return ResponseEntity.ok(generateResponse(itemService.getItemPage(pageNumber, size, f)));
    }

    /**
     * Method maps a String of a json filter object, into a object of the Filter class.
     * @param json String of a json filter object.
     * @return Filter object.
     * @throws JsonProcessingException
     */
    private Filter parseFilter(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Filter.class);
    }

    @PostMapping("/new-listing")
    @Operation(summary = "Register a new item", description = "Registers a new item in the database.")
    public ResponseEntity<?> registerNewItem(@RequestBody ItemDTO itemDTO, HttpServletRequest request) {

        // Extract userID from JWT
        String jwtToken = cookieService.extractTokenFromCookie(request);
        String email = jwtService.extractUsername(jwtToken);
        User user = userService.findByEmail(email);

        // Save the item with the user object
        ItemDTO item = itemService.saveItem(itemDTO, user);
        if (item == null) {
            return ResponseEntity.badRequest().build();
        }

        logger.info("Received api call for registering a new item: " + itemDTO);
        return ResponseEntity.ok().body("Item saved");
    }


}
