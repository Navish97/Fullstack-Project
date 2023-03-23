package ntnu.idatt2105.project.backend.repository;

import ntnu.idatt2105.project.backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Override
    Optional<Message> findById(Long aLong);

    @Query("SELECT m FROM Message m WHERE m.sender.id = :chatid OR m.receiver.id = :chatid")
    List<Message> findByChatid(@Param("chatid") String chatid);
}