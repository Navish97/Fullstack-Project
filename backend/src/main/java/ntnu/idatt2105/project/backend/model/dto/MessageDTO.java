package ntnu.idatt2105.project.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ntnu.idatt2105.project.backend.model.Message;
import ntnu.idatt2105.project.backend.model.User;

/**
 * MessageDTO. Holds all non-sensitive message data.
 */
@Getter
@Setter
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private boolean sent;
    private String senderId;
    private String senderName;
    private String message;
    private Long chatId;

    public MessageDTO(Message m, User u){
        this.id=m.getId();
        if(m.getReceiver() == u){
            sent = false;
            this.senderId = m.getSender().getId();
            this.senderName =m.getSender().getName();
        }
        else{
            sent = true;
            this.senderId = u.getId();
            this.senderName = u.getName();
        }
        this.message = m.getMessage();
        this.chatId = m.getChat().getId();
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", sent=" + sent +
                ", senderId='" + senderId + '\'' +
                ", senderName='" + senderName + '\'' +
                ", message='" + message + '\'' +
                ", chatId=" + chatId +
                '}';
    }
}
