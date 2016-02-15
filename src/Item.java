import java.time.LocalDate;

public class Item {

    private boolean isPromotion = false;
    private Double price;
    private Double originalPrice;
    String name;
    private LocalDate beginPromoDate;
    private LocalDate lastPriceChangeDate;

    public Item(String name, Double price) {
        this.price = price;
        this.name = name;
    }

    public void expirePromotion() {
        setIsPromotion(false);
        setOriginalPrice(null);
    }

    //Setters and Getters
    public boolean getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(boolean isPromotion) {
        this.isPromotion = isPromotion;
    }

    public Double getPrice() {
        return price;
    }

    public void calculatePrice(Double price, Boolean isAddition) {
        if (isAddition) {
            this.price += price;
        } else {
            this.price -= price;
        }
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }


    public LocalDate getBeginPromoDate() {
        return beginPromoDate;
    }

    public void setBeginPromoDate(LocalDate beginPromoDate) {
        this.beginPromoDate = beginPromoDate;
    }

    public LocalDate getLastPriceChangeDate () {
        return lastPriceChangeDate;
    }

    public void setLastPriceChangeDate (LocalDate lastPriceChangeDate) {
        this.lastPriceChangeDate = lastPriceChangeDate;
    }

    public void setOriginalPrice(Double originalPrice){
        this.originalPrice = originalPrice;
    }
}
