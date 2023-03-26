package ntnu.idatt2105.project.backend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * Returns a list of chat objects for the user with the given email address.
     *
     * @param request   HTTP request containing the user's JWT token.
     * @return ResponseEntity containing a list of chat objects for the user.
     * @throws ResponseEntity bad request response with http status code 400 if chat is not found.
     */
    @Operation(summary = "Gets a user's chats",
            description = "Returns a list of chat objects for the user with the given email address.",
            parameters = {
                    @Parameter(name = "email",
                            description = "The email address of the user to retrieve chats for.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "The list of chats for the user.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ChatDTO.class)))
            })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/load-chats")
    public ResponseEntity<?> getChats(HttpServletRequest request){

        logger.info("Received request for retrieving chats for user");
        String jwtToken = cookieService.extractTokenFromCookie(request);
        String email = jwtService.extractUsername(jwtToken);
        User user = userService.findByEmail(email);

        List<ChatDTO> chats = chatService.getChats(user);
        if(chats == null) {
            logger.info("No chats found for user");
            return ResponseEntity.badRequest().build();
        }
        logger.info("Returning the following chats for user " + user.getId() + user.getName() + " " + chats);
        return ResponseEntity.ok(generateResponseChats(chats));

    }

    /**
     * Retrieves and returns a list of messages for a given chat ID that the user is authorized to access.
     *
     * @param request   the HTTP request containing the JWT Token.
     * @param chatId    the ID of the chat to retrieve messages from.
     * @return  A ResponseEntity containing a list of MessageDTO objects.
     * @throws  ResponseEntity bad request response with http status code 400
     * if the chat ID is invalid or the user does not have access to this chat.
     */
    @Operation(summary = "Retrieve messages for a given chat ID",
            description = "Retrieves and returns a list of messages for a given chat ID that the user is authorized to access.",
            parameters = {
                    @Parameter(name = "chatId",
                            description = "The ID of the chat to retrieve messages from.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Contains a list of messages for the chat.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MessageDTO.class)))
            })
    @PreAuthorize("isAuthenticated()")
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

    /**
     * Sends a message to a chat or creates a new chat if it does not exist.
     *
     * @param request  The HTTP request containing the JWT token.
     * @param requestBody  A map containing the message, chat ID, and item ID.
     * @return ResponseEntity containing the sent message and chat information.
     * @throws ResponseEntity bad request response with http status code 400 if the chatDTO or messageDTO are null.
     */
    @Operation(summary = "Api for sending a message or creating a new chat",
            description = "Sends a message to a chat or creates a new chat if it does not exist.",
            parameters = {
                    @Parameter(name = "requestBody",
                            description = "A map containing the message, chat ID, and item ID.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "The sent message and chat information.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ChatDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request.",
                            content = @Content)
            })
    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessage(HttpServletRequest request, @RequestBody Map<String, Object> requestBody){
        logger.info("Received request for sending message");
        String jwtToken = cookieService.extractTokenFromCookie(request);
        String email = jwtService.extractUsername(jwtToken);
        User user = userService.findByEmail(email);

        logger.info("Extracted user from JWT");
        String message = (String) requestBody.get("message");
        Long chatId = ((Number) requestBody.get("chatId")).longValue();
        Long itemId = ((Number) requestBody.get("itemId")).longValue();
        ChatDTO chatDTO;
        if(chatId == -1){
            logger.info("Chat ID is -1, creating new chat");
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
        return ResponseEntity.ok(generateResponseChat(messageDTO, chatId));

    }

    /**
     * Generates a response map for a chat.
     *
     * @param message  The message to be sent.
     * @param chatId  The ID of the chat.
     * @return  A map containing the message and chat ID.
     */
    private Map<String, Object> generateResponseChat(final MessageDTO message, final Long chatId){
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("chatId", chatId);
        return response;
    }

    /**
     * Generates a response map for a list of messages.
     *
     * @param messages  The list of messages to be sent.
     * @return  A map containing the list of messages and the total number of messages.
     */
    private Map<String, Object> generateResponseMessages(final List<MessageDTO> messages){
        Map<String, Object> response = new HashMap<>();
        response.put("messages", messages);
        response.put("total-messages", messages.size());
        return response;
    }

    /**
     * Generates a response map for a list of chats.
     * @param chats The list of chats to be sent.
     * @return A map containing the list of chats and the total number of chats.
     */
    private Map<String, Object> generateResponseChats(final List<ChatDTO> chats){
        Map<String, Object> response = new HashMap<>();
        response.put("chats",chats);
        response.put("total-chats", chats.size());
        return response;
    }
}
