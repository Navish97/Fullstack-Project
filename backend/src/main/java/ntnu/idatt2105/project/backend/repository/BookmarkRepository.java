package ntnu.idatt2105.project.backend.repository;

import ntnu.idatt2105.project.backend.model.Bookmark;
import ntnu.idatt2105.project.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ntnu.idatt2105.project.backend.model.dto.BookmarkDTO;
import org.yaml.snakeyaml.Yaml;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Bookmarks. Uses JPARepository to interact with the database.
 */
@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    /**
     * Queries the databsae for all bookmarks connected a user id and maps them to DTOs.
     * @param userId
     * @return
     */
    @Query(value = "SELECT new ntnu.idatt2105.project.backend.model.dto.BookmarkDTO(b.id, b.user.id, b.item.id) FROM Bookmark b WHERE b.user.id = :userId")
    List<BookmarkDTO> findAllBookmarkDTOsByUserId(String userId);

    /**
     * Queries the database for all bookmarks connected to a user id.
     * @param userId
     * @return
     */
    @Query(value = "SELECT b FROM Bookmark b WHERE b.user.id = :userId")
    List<Bookmark> findAllBookmarksByUserId(String userId);

    /**
     * Queries the database for a page of bookmarks connected to a user.
     * @param userId
     * @param pageable
     * @return
     */
    Page<Bookmark> findBookmarkPageByUserId(String userId, Pageable pageable);

    /**
     * Queries the database for all bookmarks connected to an item.
     * @param itemId
     * @return
     */
    List<Bookmark> findAllByItemId(long itemId);

    /**
     * Queries the database for all bookmarks connected to a user and item.
     * @param userId
     * @param itemId
     * @return
     */
    Optional<Bookmark> findByUserIdAndItemId(String userId, Long itemId);
}