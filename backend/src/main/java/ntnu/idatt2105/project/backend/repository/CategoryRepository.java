package ntnu.idatt2105.project.backend.repository;

import ntnu.idatt2105.project.backend.model.Category;
import ntnu.idatt2105.project.backend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPARepository for interacting with database. Only uses standard JPA methods.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}