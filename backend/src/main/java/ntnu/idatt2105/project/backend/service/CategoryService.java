package ntnu.idatt2105.project.backend.service;


import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.Category;
import ntnu.idatt2105.project.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for Category
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Returns all categories.
     * @return
     */
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    /**
     * Returns a category based on id.
     * @param id
     * @return
     */
    public Optional<Category> getCategoryById(Long id){
        return categoryRepository.findById(id);
    }

    /**
     * Deletes a category
     * @param id
     * @return
     */
    public boolean deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
        return true;
    }

    /**
     * Creates a new category
     * @param newCategory
     */
    public void createCategory(Category newCategory) {
        categoryRepository.save(newCategory);
    }
}
