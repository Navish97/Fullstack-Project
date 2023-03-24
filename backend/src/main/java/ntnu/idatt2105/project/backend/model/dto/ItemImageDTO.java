package ntnu.idatt2105.project.backend.model.dto;

import lombok.*;
import ntnu.idatt2105.project.backend.model.ItemImage;

import java.util.Base64;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemImageDTO {
    private Long id;
    private String contentType;
    private String data;

    /**
     * Copy constructor to construct an ItemImageDTO from an ItemImage object.
     *
     * @param itemImage the ItemImage object to copy from
     */
    public ItemImageDTO(ItemImage itemImage) {
        this.id = itemImage.getId();
        this.contentType = itemImage.getContentType();
        this.data = "data:" + itemImage.getContentType() +
                ";base64," + Base64.getEncoder().encodeToString(itemImage.getData());
    }
}