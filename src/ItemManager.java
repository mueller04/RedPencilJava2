import java.time.LocalDate;

public class ItemManager {

    private Item item;

    public ItemManager(Item item){
        this.item = item;
    }



    public void increasePrice(Double price) {
        calculatePrice(price, true);
        expirePromotion();
    }

    public void beginPromotion() {
        setPromotionBeginDate(LocalDate.now());
        setIsPromotion(true);
    }

    public void expirePromotion() {
        item.expirePromotion();
    }

    public void reducePrice(Double priceToReduce) {
        this.setOriginalPrice(this.getPrice());
        this.calculatePrice(priceToReduce, false);
        this.setLastPriceChangeDate(LocalDate.now());
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

    public boolean getIsPromotion() {return item.getIsPromotion();}

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
