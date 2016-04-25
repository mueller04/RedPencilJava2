/**
 * Created by Mike on 4/25/16.
 */
public class Program {

    public static void main(String[] args){

        Item item = new Item("pen", 5.00);
        ItemManager itemManager = new ItemManager(item);
        PromotionManager promoManager = new PromotionManager();
        PromotionRequestor promotionRequestor = new PromotionRequestor(itemManager, promoManager);

        promotionRequestor.requestReducePriceAndBeginPromotion(4.00);


        displayPromotion(itemManager, promoManager);
    }

    private static String isPromotion(PromotionManager promoManager, ItemManager itemManager){
        boolean isPromotion = promoManager.isPromotion(itemManager.getItem());
        String returnValue = "false";
        if (isPromotion){
            returnValue = "true";
        }
        return returnValue;
    }

    private static void displayPromotion(ItemManager itemManager, PromotionManager promoManager){
        String isPromotionString = isPromotion(promoManager, itemManager);

        System.out.println("isPromotion: " + isPromotionString
                + "\nCurrent Price: " + itemManager.getPrice().toString()
                + "\nBeginPromoDate: " + itemManager.getPromotionDate()
                + "\nLastPriceChangeDate: " + itemManager.getItem().getLastPriceChangeDate());
    }

}
