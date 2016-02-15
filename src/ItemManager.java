import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ItemManager {

    private Item item;

    public ItemManager(Item item){
        this.item = item;
    }

    public boolean isPromotion(){
        LocalDate now = LocalDate.now();
        if (item.getBeginPromoDate() != null ) {
            if (ChronoUnit.DAYS.between(item.getBeginPromoDate(), now) > 30) {
                item.setIsPromotion(false);
            }
        }
            return item.getIsPromotion();
    }


    //is it an okay design pattern to pass this
    public void increasePrice(Double price) {
        item.calculatePrice(price, true);
        item.expirePromotion();
    }

    public void beginPromotion() {
        item.setBeginPromoDate(LocalDate.now());
        item.setIsPromotion(true);
    }

    //Setters and Getters
    public void setOriginalPrice(Double originalPrice) {
        item.setOriginalPrice(originalPrice);
    }

    public void calculatePrice(Double price, Boolean isAddition) {
        item.calculatePrice(price, isAddition);
    }

    public void setIsPromotion(boolean isPromotion) {
        item.setIsPromotion(isPromotion);
    }

    public LocalDate getPromotionDate(){
            return item.getBeginPromoDate();
    }

    public void setPromotionBeginDate(LocalDate promotionBeginDate){
        item.setBeginPromoDate(promotionBeginDate);
    }

    public Double getPrice(){
        return item.getPrice();
    }

    public Double getOriginalPrice(){
        return item.getOriginalPrice();
    }

    public Item getItem() {
        return item;
    }

    //Test Methods
    public void setLastPriceChangeDate(LocalDate date){
        item.setLastPriceChangeDate(date);
    }

}
