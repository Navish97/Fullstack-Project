package ntnu.idatt2105.project.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ntnu.idatt2105.project.backend.model.Item;

import java.math.BigDecimal;

/**
 * Data Transfer Object for the Item class.
 */
@Getter
@Setter
@AllArgsConstructor
public class ItemDTO {
    private long id;
    private String userId;
    private String userName;
    private String userEmail;
    private long categoryId;
    private String title;
    private String description;
    private BigDecimal price;
    private String longitude;
    private String latitude;
    private String imageUrls;


    /**
     * Copy constructor to construct an ItemDTO from an Item object. 
     * @param item
     */
    public ItemDTO(Item item){
        this.id = item.getId();
        this.userId = item.getUser().getId();
        this.userName = item.getUser().getName();
        this.userEmail = item.getUser().getEmail();
        this.title = item.getTitle();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.longitude = item.getLongitude();
        this.latitude = item.getLatitude();
        this.imageUrls = item.getImageUrls();
    }
}
