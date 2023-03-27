package ntnu.idatt2105.project.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.dto.ErrorResponse;
import ntnu.idatt2105.project.backend.model.Category;
import ntnu.idatt2105.project.backend.model.dto.response.SuccessResponse;
import ntnu.idatt2105.project.backend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


/**

 The CategoryController class handles incoming requests related to categories.
 It allows the retrieval of all categories, deletion of categories by ID, and creation of new categories.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    /**
     * The CategoryService instance used to handle category data.
     */
    private final CategoryService categoryService;

    /**
     * The logger instance for the class.
     */
    Logger logger = Logger.getLogger(ProfileController.class.getName());

    /**
     * Retrieves a list of all categories that can be used for items.
     * Checks first if the user is authenticated by using the @PreAuthorize annotation.
     *
     * @param request The HttpServletRequest.
     * @return List of categories.
     */
    @Operation(summary = "Get all categories",
            description = "Retrieve a list of all categories that can be used for items",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of categories"),
                    @ApiResponse(responseCode = "404", description = "No categories found")
            })
    @GetMapping("/categories")
    public ResponseEntity<?> getMyCategories(HttpServletRequest request) {
        List<Category> categories = categoryService.getAllCategories();
        logger.info("Categories: " + categories);

        if (categories == null){
            logger.info("No categories found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No categories found"));
        }
        logger.info("Categories: " + categories);
        return ResponseEntity.ok(categories);
    }

    /**
     * Retrieves the image that belongs to a category by ID.
     * Checks first if the user is authenticated by using the @PreAuthorize annotation.
     *
     * @param id The ID of the category.
     * @return The image path of the category.
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
    public ResponseEntity<?> findCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        logger.info("Icon URL: " + category);
        if (category.isEmpty()){
            logger.info("No icon URL found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No icon URL found"));
        }
        logger.info("Icon URL: " + category);
        return ResponseEntity.ok(category);
    }


    /**

     The deleteCategoryById method handles the deletion of a category by its ID.

     The method is accessible only by authenticated users with an ADMIN role.

     @param id The ID of the category to be deleted.

     @return ResponseEntity with a SuccessResponse containing a message indicating successful deletion if

     the category was successfully deleted, or a ResponseEntity with an ErrorResponse containing a message

     indicating failure to delete if the category was not found.
     */
    @Operation(summary = "Delete category by ID",
            description = "Delete a category by its ID",
            parameters = {
                    @Parameter(name = "id",
                            description = "The ID of the category to delete")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Category successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            })
    @DeleteMapping("/categories/{id}")
    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        boolean isDeleted = categoryService.deleteCategoryById(id);
        logger.info("Category deleted: " + isDeleted);

        if (isDeleted) {
            return ResponseEntity.ok(new SuccessResponse("Category was successfully deleted", 200));
        } else {
            logger.info("Category not deleted");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Category not deleted"));
        }
    }


    /**

     The createCategory method handles the creation of a new category.

     The method is accessible only by authenticated users with an ADMIN role.

     @param newCategoryRequest The request body containing the new category information.

     @return ResponseEntity with a SuccessResponse containing a message indicating successful creation if

     the category was successfully created, or a ResponseEntity with an ErrorResponse containing a message

     indicating a failure to create if the request body was invalid.
     */
    @Operation(summary = "Create new category",
            description = "Create a new category",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category object",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Category.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category successfully created",
                            content = @Content(schema = @Schema(implementation = Category.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            })
    @PostMapping("/categories/new")
    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    public ResponseEntity<?> createCategory(@RequestBody Category newCategoryRequest) {
        try{
            Category newCategory = new Category();
            newCategory.setType(newCategoryRequest.getType());
            newCategory.setIcon_url(newCategoryRequest.getIcon_url());
            categoryService.createCategory(newCategory);
            logger.info("Category created: " + newCategory);

            return ResponseEntity.ok(new SuccessResponse("Category was successfully created", 200));
        }
        catch (Exception e){
            logger.info("Category not created");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Category not created"));
        }
    }


}