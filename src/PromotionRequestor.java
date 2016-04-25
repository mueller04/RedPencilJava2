public class PromotionRequestor {

ItemManager itemManager;
PromotionManager promotionManager;

    public PromotionRequestor(ItemManager itemManager, PromotionManager promotionManager) {
        this.itemManager = itemManager;
        this.promotionManager = promotionManager;
    }


    public void requestReducePriceAndBeginPromotion(Double priceToReduce){

        if (promotionManager.areCreatePromotionConditionsMet(priceToReduce, itemManager.getItem())){
            itemManager.beginPromotion();
        }

        if (priceToReduce < itemManager.getPrice()){
            itemManager.reducePrice(priceToReduce);
        }
    }

}