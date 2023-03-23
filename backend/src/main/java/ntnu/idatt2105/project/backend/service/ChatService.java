package ntnu.idatt2105.project.backend.service;

import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.Chat;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.model.dto.ChatDTO;
import ntnu.idatt2105.project.backend.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public List<ChatDTO> getChats(User user){
        List<Chat> chats = chatRepository.findAllByUser(user);
        List<ChatDTO> chatDTOs = chats.stream().map(chat -> new ChatDTO(chat, user)).collect(Collectors.toList());
        return chatDTOs;
    }

}
