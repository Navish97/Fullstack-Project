package ntnu.idatt2105.project.backend.repository;

import ntnu.idatt2105.project.backend.model.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPARepository for interacting with Item image connection part of the database.
 */
@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    /**
     * Finds all item image objects based on id.
     * @param aLong
     * @return
     */
    @Override
    Optional<ItemImage> findById(Long aLong);


    void deleteByItemId(Long itemId);

    List<ItemImage> findAllByItemId(long itemId);
}