package ntnu.idatt2105.project.backend.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.model.dto.ChatDTO;
import ntnu.idatt2105.project.backend.model.dto.MessageDTO;
import ntnu.idatt2105.project.backend.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(generateResponseChat(chats));

    }

    @GetMapping("/load-messages")
    public ResponseEntity<?> getMessages(HttpServletRequest request, @RequestParam Long chatId){
        // Extract userID from JWT
        String jwtToken = cookieService.extractTokenFromCookie(request);
        String email = jwtService.extractUsername(jwtToken);
        User user = userService.findByEmail(email);

        List<MessageDTO> messageDTOs = messageService.getMessages(chatId, user);
        if(messageDTOs == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(generateResponseMessage(messageDTOs));
    }

    private Map<String, Object> generateResponseMessage(final List<MessageDTO> messages){
        Map<String, Object> response = new HashMap<>();
        response.put("messages", messages);
        response.put("total-messages", messages.size());
        return response;
    }

    private Map<String, Object> generateResponseChat(final List<ChatDTO> chats){
        Map<String, Object> response = new HashMap<>();
        response.put("chats",chats);
        response.put("total-chats", chats.size());
        return response;
    }
}
