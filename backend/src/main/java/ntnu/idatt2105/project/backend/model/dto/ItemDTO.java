package ntnu.idatt2105.project.backend.model.dto;

import lombok.Getter;
import lombok.Setter;
import ntnu.idatt2105.project.backend.model.Item;

import java.math.BigDecimal;

/**
 * Data Transfer Object for the Item class.
 */
@Getter
@Setter
public class ItemDTO {
    private long id;
    private String userId;
    private long categoryId;
    private String title;
    private String description;
    private BigDecimal price;
    private String longitude;
    private String latitude;
    private String imageUrls;

    /**
     * Constructor with every parameter given.
     * @param id
     * @param userId
     * @param title
     * @param description
     * @param price
     * @param longitude
     * @param latitude
     * @param imageUrls
     */
    public ItemDTO(long id, String userId, String title, String description, BigDecimal price, String longitude, String latitude, String imageUrls) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imageUrls = imageUrls;
    }

    /**
     * Copy constructor to construct an ItemDTO from an Item object. 
     * @param item
     */
    public ItemDTO(Item item){
        this.id = item.getId();
        this.userId = item.getUser().getId();
        this.title = item.getTitle();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.longitude = item.getLongitude();
        this.latitude = item.getLatitude();
        this.imageUrls = item.getImageUrls();
    }
}
