package ntnu.idatt2105.project.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.dto.ErrorResponse;
import ntnu.idatt2105.project.backend.model.Category;
import ntnu.idatt2105.project.backend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    Logger logger = Logger.getLogger(ProfileController.class.getName());

    /**
     * Gets all categories. Checks first if the user is authenticated by using the @PreAuthorize annotation.
     *
     * @param request The HttpServletRequest
     * @return List of categories
     */
    @Operation(summary = "Get all categories",
            description = "Retrieve a list of all categories that can be used for items",
            responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of categories"),
            @ApiResponse(responseCode = "404", description = "No categories found")}
    )
    @GetMapping("/categories")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyCategories(HttpServletRequest request) {
        List<Category> categories = categoryService.getAllCategories();
        logger.info("Categories: " + categories);

        if (categories == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No categories found"));
        }
        return ResponseEntity.ok(categories);
    }

    /**
     * Gets the image that belongs to a category.
     * Checks first if the user is authenticated by using the @PreAuthorize annotation.
     *
     * @param id The id of the category
     * @return The image path of the category
     */
    @Operation(summary = "Get category icon by ID",
            description = "Retrieve the icon URL for a category by ID",
            parameters = {
                    @Parameter(name = "id",
                            description = "The ID of the category")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the icon URL for the category"),
                    @ApiResponse(responseCode = "404", description = "No icon URL found")
            })
    @GetMapping("{id}/icon")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        logger.info("Icon URL: " + category);
        if (category.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No icon URL found"));
        }
        return ResponseEntity.ok(category);
    }
}