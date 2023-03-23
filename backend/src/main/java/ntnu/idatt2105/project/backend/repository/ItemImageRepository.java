package ntnu.idatt2105.project.backend.repository;

import ntnu.idatt2105.project.backend.model.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
}