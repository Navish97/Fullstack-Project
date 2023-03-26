package ntnu.idatt2105.project.backend;

import jakarta.servlet.http.HttpServletRequest;
import ntnu.idatt2105.project.backend.controller.CategoryController;
import ntnu.idatt2105.project.backend.model.Category;
import ntnu.idatt2105.project.backend.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private HttpServletRequest request;

    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.categoryController = new CategoryController(categoryService);
    }

    @Test
    void testGetMyCategories() {
        List<Category> categories = new ArrayList<>();
        Category category = new Category(1L, "Bike", "Icon");
        Category category2 = new Category(2L, "Car", "Icon");

        categories.add(category);
        categories.add(category2);
        when(categoryService.getAllCategories()).thenReturn(categories);

        ResponseEntity<?> response = categoryController.getMyCategories(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        assertEquals(2, ((List<?>) response.getBody()).size());
        verify(categoryService).getAllCategories();
    }

    @Test
    void testFindCategoryById() {
        Long categoryId = 2L;
        Category category = new Category(categoryId, "Car", "Icon");

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(category));

        ResponseEntity<?> response = categoryController.findCategoryById(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(categoryService).getCategoryById(categoryId);
    }

    @Test
    void testFindCategoryByIdNotFound() {
        Long categoryId = 1L;

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = categoryController.findCategoryById(categoryId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(categoryService).getCategoryById(categoryId);
    }
}