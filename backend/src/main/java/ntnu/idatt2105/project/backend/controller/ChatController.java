package ntnu.idatt2105.project.backend.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.Item;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.model.dto.ChatDTO;
import ntnu.idatt2105.project.backend.model.dto.MessageDTO;
import ntnu.idatt2105.project.backend.service.*;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
@Tag(name = "Chat Controller", description ="Controller to retrieve and send chats")
public class ChatController {
    Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final CookieService cookieService;
    private final JwtService jwtService;
    private final UserService userService;
    private final ChatService chatService;
    private final MessageService messageService;
    private final ItemService itemService;


    @GetMapping("/load-chats")
    public ResponseEntity<?> getChats(HttpServletRequest request){

        // Extract userID from JWT
        String jwtToken = cookieService.extractTokenFromCookie(request);
        String email = jwtService.extractUsername(jwtToken);
        User user = userService.findByEmail(email);

        List<ChatDTO> chats = chatService.getChats(user);
        if(chats == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(generateResponseChats(chats));

    }

    @GetMapping("/load-messages")
    public ResponseEntity<?> getMessages(HttpServletRequest request, @RequestParam Long chatId){
        // Extract userID from JWT
        String jwtToken = cookieService.extractTokenFromCookie(request);
        String email = jwtService.extractUsername(jwtToken);
        User user = userService.findByEmail(email);

        logger.info("Retrieved request for retrieving chat " + chatId + " for user" + user.getId() + user.getName());

        List<MessageDTO> messageDTOs = messageService.getMessages(chatId, user);
        if(messageDTOs == null){
            return ResponseEntity.badRequest().build();
        }
        logger.info("Returning the following messages in chat " + chatId + " "  + messageDTOs);
        return ResponseEntity.ok(generateResponseMessages(messageDTOs));
    }

    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessage(HttpServletRequest request, @RequestBody Map<String, Object> requestBody){
        // Extract userID from JWT
        String jwtToken = cookieService.extractTokenFromCookie(request);
        String email = jwtService.extractUsername(jwtToken);
        User user = userService.findByEmail(email);

        String message = (String) requestBody.get("message");
        Long chatId = ((Number) requestBody.get("chatId")).longValue();
        Long itemId = ((Number) requestBody.get("itemId")).longValue();
        ChatDTO chatDTO;
        if(chatId == -1){
            logger.info("Creating new chat");
            chatDTO = chatService.newChat(user, itemId);
            chatId = chatDTO.getId();
            if(chatDTO == null){
                return ResponseEntity.badRequest().build();
            }
        }

        logger.info("Received message from user: " + user.getId() + " in chat " + chatId);

        MessageDTO messageDTO = messageService.sendMessage(chatId, user, message);
        if(messageDTO == null){
            return ResponseEntity.badRequest().build();
        }
        logger.info("Messages stored to database, returning sent message to user in messageDTO");
        return ResponseEntity.ok(messageDTO);

    }

    private Map<String, Object> generateResponseMessages(final List<MessageDTO> messages){
        Map<String, Object> response = new HashMap<>();
        response.put("messages", messages);
        response.put("total-messages", messages.size());
        return response;
    }

    private Map<String, Object> generateResponseChats(final List<ChatDTO> chats){
        Map<String, Object> response = new HashMap<>();
        response.put("chats",chats);
        response.put("total-chats", chats.size());
        return response;
    }
}
