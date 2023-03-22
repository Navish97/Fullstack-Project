package ntnu.idatt2105.project.backend.controller;

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
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Login Controller", description = "Controller to handle user login")
public class CategoryController {

    private final CategoryService categoryService;

    Logger logger = Logger.getLogger(ProfileController.class.getName());


    @GetMapping("/categories")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
        List<Category> categories = categoryService.getAllCategories();
        logger.info("Categories: " + categories);

        if (categories == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No categories found"));
        }
        return ResponseEntity.ok(categories);
    }
}