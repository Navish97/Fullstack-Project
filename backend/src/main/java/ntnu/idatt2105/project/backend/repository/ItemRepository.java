package ntnu.idatt2105.project.backend.repository;

import ntnu.idatt2105.project.backend.model.Item;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Override
    Optional<Item> findById(Long aLong);

    @Query(value = "SELECT i FROM Item i WHERE (:minPrice IS NULL OR i.price >= :minPrice) AND (:maxPrice IS NULL OR i.price <= :maxPrice)")
    Page<Item> getItemsByPrice(int minPrice, int maxPrice, final Pageable pageable);
    @Query(value = "SELECT i FROM Item i")
    Page<Item> getItems(final Pageable pageable);
}