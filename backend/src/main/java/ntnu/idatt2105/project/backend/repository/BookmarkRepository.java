package ntnu.idatt2105.project.backend.repository;

import ntnu.idatt2105.project.backend.model.Bookmark;
import ntnu.idatt2105.project.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ntnu.idatt2105.project.backend.model.dto.BookmarkDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    @Query(value = "SELECT new ntnu.idatt2105.project.backend.model.dto.BookmarkDTO(b.id, b.user.id, b.item.id) FROM Bookmark b WHERE b.user.id = :userId")
    List<BookmarkDTO> findAllBookmarksByUserId(String userId);

    Optional<Bookmark> findByUserIdAndItemId(String userId, Long itemId);
}