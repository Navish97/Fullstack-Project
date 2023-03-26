package ntnu.idatt2105.project.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Class represents a filter used to filter searches in the item repository.
 */
@Getter
@Setter
public class Filter {
    int minPrice;
    int maxPrice;
    boolean usedValue;
    boolean newValue;
    long category;
    String search;

    /**
     * Default constructor for the Filter class.
     *
     * @param minPrice
     * @param maxPrice
     * @param usedValue
     * @param newValue
     * @param search
     */
    public Filter(int minPrice, int maxPrice, boolean usedValue, boolean newValue, long category, String search) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.usedValue = usedValue;
        this.newValue = newValue;
        this.category = category;
        this.search = search;
    }

    /**
     * Empty constructor, used by the ObjectMapper.
     */
    public Filter() {
    }
}
