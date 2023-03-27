package ntnu.idatt2105.project.backend.service;

import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.Chat;
import ntnu.idatt2105.project.backend.model.Item;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.model.dto.ChatDTO;
import ntnu.idatt2105.project.backend.repository.ChatRepository;
import ntnu.idatt2105.project.backend.repository.ItemRepository;
import ntnu.idatt2105.project.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for chat functionality.
 */
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ItemRepository itemRepository;

    /**
     * Returns a list of all chats connected to a user.
     * @param user
     * @return
     */
    public List<ChatDTO> getChats(User user){
        List<Chat> chats = chatRepository.findAllByUser(user);
        List<ChatDTO> chatDTOs = chats.stream().map(chat -> new ChatDTO(chat, user)).collect(Collectors.toList());
        return chatDTOs;
    }

    /**
     * Returns a chatDTO object after creating a new chat in the repository.
     * @param user
     * @param itemId
     * @return
     */
    public ChatDTO newChat(User user, Long itemId){
        Chat chat = new Chat();
        Item item = itemRepository.findById(itemId).orElse(null);
        if(item == null){
            return null;
        }
        //TODO: handle exception if item is null;
        chat.setItem(item);
        chat.setUserOne(user);
        chat.setUserTwo(item.getUser());
        chatRepository.save(chat);
        return new ChatDTO(chat, user);
    }

}
