package ntnu.idatt2105.project.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntnu.idatt2105.project.backend.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Item data transfer object. Returns only relevant non-sensitive item and user data.
 */
@Getter
@Setter
@NoArgsConstructor
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
    private Double longitude;
    private Double latitude;
    private List<ItemImageDTO> images;

    /**
     * Copy constructor to construct an ItemDTO from an Item object.
     *
     * @param item the Item object to copy from
     */
    public ItemDTO(Item item) {
        this.id = item.getId();
        this.userId = item.getUser().getId();
        this.userName = item.getUser().getName();
        this.userEmail = item.getUser().getEmail();
        this.categoryId = item.getCategory().getId();
        this.title = item.getTitle();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.longitude = item.getLongitude();
        this.latitude = item.getLatitude();
        this.images = item.getImages().stream()
                .map(ItemImageDTO::new)
                .collect(Collectors.toList());
    }
}
