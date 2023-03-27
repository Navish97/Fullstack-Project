package ntnu.idatt2105.project.backend.repository;

import ntnu.idatt2105.project.backend.model.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    @Override
    Optional<ItemImage> findById(Long aLong);

    void deleteByItemId(Long itemId);

    List<ItemImage> findAllByItemId(long itemId);
}