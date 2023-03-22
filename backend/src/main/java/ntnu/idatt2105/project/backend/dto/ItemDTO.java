package ntnu.idatt2105.project.backend.dto;

import lombok.Getter;
import lombok.Setter;
import ntnu.idatt2105.project.backend.model.Item;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemDTO {
    private long id;
    private String userId;
    private long categoryId;
    private String title;
    private String briefDescription;
    private String description;
    private BigDecimal price;
    private String longitude;
    private String latitude;
    private String imageUrls;

    public ItemDTO(long id, String userId, String title, String briefDescription, String description, BigDecimal price, String longitude, String latitude, String imageUrls) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.briefDescription = briefDescription;
        this.description = description;
        this.price = price;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imageUrls = imageUrls;
    }
    public ItemDTO(Item item){
        this.id = item.getId();
        this.userId = item.getUser().getId();
        this.title = item.getTitle();
        this.briefDescription = item.getBriefDescription();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.longitude = item.getLongitude();
        this.latitude = item.getLatitude();
        this.imageUrls = item.getImageUrls();
    }
}
