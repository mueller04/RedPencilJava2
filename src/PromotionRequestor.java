import java.time.LocalDate;

public class PromotionRequestor {

ItemManager itemManager;
PromotionManager promotionManager;

    public PromotionRequestor(ItemManager itemManager, PromotionManager promotionManager) {
        this.itemManager = itemManager;
        this.promotionManager = promotionManager;
    }


    public void requestReducePriceAndBeginPromotion(Double price){

        if (promotionManager.areCreatePromotionConditionsMet(price, itemManager.getItem())){
            itemManager.beginPromotion();
        }

        if (price < itemManager.getPrice()){
            itemManager.setOriginalPrice(itemManager.getPrice());
            itemManager.calculatePrice(price, false);
            itemManager.setLastPriceChangeDate(LocalDate.now());
        }
    }

}

//comment