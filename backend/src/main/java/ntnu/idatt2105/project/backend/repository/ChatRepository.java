package ntnu.idatt2105.project.backend.repository;

import ntnu.idatt2105.project.backend.model.Chat;
import ntnu.idatt2105.project.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPARepository for interacting with Chat part of the database.
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    /**
     * Queries database for all chats with specified user id.
     * @param aLong
     * @return
     */
    @Override
    Optional<Chat> findById(Long aLong);

    /**
     * Queries database for all chats with specified user object.
     * @param user
     * @return
     */
    @Query("SELECT c from Chat c WHERE c.userOne = :user OR c.userTwo = :user")
    List<Chat> findAllByUser(@Param("user") User user);

    /**
     * Finds all chats connected to an item.
     * @param itemId
     * @return
     */
    List<Chat> findAllByItemId(long itemId);

}
