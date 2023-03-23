package ntnu.idatt2105.project.backend.repository;

import ntnu.idatt2105.project.backend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Override
    Optional<Chat> findById(Long aLong);
}
