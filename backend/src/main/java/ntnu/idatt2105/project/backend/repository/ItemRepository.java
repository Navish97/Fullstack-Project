package ntnu.idatt2105.project.backend.repository;

import jakarta.transaction.Transactional;
import ntnu.idatt2105.project.backend.model.Category;
import ntnu.idatt2105.project.backend.model.Item;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
/**
 * JPARepository class for Item elements.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
    /**
     * Finds an item based on the item id.
     * @param aLong
     * @return
     */
    @Override
    Optional<Item> findById(Long aLong);

    /**
     * Finds a page of item based on the user id.
     * @param userId
     * @param pageable
     * @return
     */
    Page<Item> findByUserId(String userId, Pageable pageable);

    /**
     * Queries the database for items, with the specified filter. Returns a page of filtered items.
     * @param minPrice
     * @param maxPrice
     * @param category_id
     * @param search
     * @param pageable
     * @return
     */
    @Query(value = "SELECT i FROM Item i \n" +
            "WHERE (\n" +
            "    (:minPrice IS NULL AND :maxPrice IS NULL) OR \n" +
            "    (:minPrice = 0 AND :maxPrice = 0) OR\n" +
            "    (i.price >= :minPrice AND i.price <= :maxPrice)\n" +
            ") \n" +
            "AND (:category_id = 0 OR i.category.id = :category_id)\n" +
            "AND (\n" +
            "    :search IS NULL OR \n" +
            "    LOWER(i.title) LIKE CONCAT('%', LOWER(:search), '%') OR \n" +
            "    LOWER(i.description) LIKE CONCAT('%', LOWER(:search), '%')\n" +
            ")")
    Page<Item> getItemsFiltered(int minPrice, int maxPrice, long category_id, String search, final Pageable pageable);

    /**
     * Get a page of unfiltered items.
     * @param pageable
     * @return
     */
    @Query(value = "SELECT i FROM Item i")
    Page<Item> getItems(final Pageable pageable);

}