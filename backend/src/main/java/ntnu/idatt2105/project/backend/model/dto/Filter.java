package ntnu.idatt2105.project.backend.model.dto;

public class Filter {
    int minPrice;
    int maxPrice;
    boolean usedValue;
    boolean newValue;

    public Filter(int minPrice, int maxPrice, boolean usedValue, boolean newValue) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.usedValue = usedValue;
        this.newValue = newValue;
    }

    public Filter() {
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public boolean isUsedValue() {
        return usedValue;
    }

    public void setUsedValue(boolean usedValue) {
        this.usedValue = usedValue;
    }

    public boolean isNewValue() {
        return newValue;
    }

    public void setNewValue(boolean newValue) {
        this.newValue = newValue;
    }
}
