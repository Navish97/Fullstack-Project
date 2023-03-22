package ntnu.idatt2105.project.backend.service;


import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.Category;
import ntnu.idatt2105.project.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public String findIconUrlById(Long id) {
        return categoryRepository.findIconUrlById(id);
    }


}
