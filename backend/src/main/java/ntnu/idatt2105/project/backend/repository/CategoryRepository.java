package ntnu.idatt2105.project.backend.repository;

import ntnu.idatt2105.project.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Query("SELECT c.iconUrl FROM Category c WHERE c.id = ?1")
    String findIconUrlById(Long id);
}