package ntnu.idatt2105.project.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ntnu.idatt2105.project.backend.model.Chat;
import ntnu.idatt2105.project.backend.model.Item;
import ntnu.idatt2105.project.backend.model.User;

@Getter
@Setter
@AllArgsConstructor
public class ChatDTO {
    private Long id;
    private String userId;
    private String userName;
    private String userEmail;
    private ItemDTO item;

    public ChatDTO(Chat chat, User user){
        User userTwo;
        if(user == chat.getUserOne()){
            userTwo = chat.getUserTwo();
        }
        else{
            userTwo = chat.getUserOne();
        }
        this.id = chat.getId();
        this.userId = userTwo.getId();
        this.userName = userTwo.getName();
        this.userEmail = userTwo.getEmail();

        this.item = new ItemDTO(chat.getItem());
    }

}
