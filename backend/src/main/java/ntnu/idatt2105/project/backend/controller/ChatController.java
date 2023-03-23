package ntnu.idatt2105.project.backend.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.model.dto.ChatDTO;
import ntnu.idatt2105.project.backend.service.ChatService;
import ntnu.idatt2105.project.backend.service.CookieService;
import ntnu.idatt2105.project.backend.service.JwtService;
import ntnu.idatt2105.project.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        return ResponseEntity.ok(generateResponse(chats));

    }

    private Map<String, Object> generateResponse(final List<ChatDTO> chats){
        Map<String, Object> response = new HashMap<>();
        response.put("chats",chats);
        response.put("total-chats", chats.size());
        return response;
    }
}
