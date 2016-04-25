
public class IsPriceReductionLessThan30PercentofOriginalPriceDuringActivePromotion implements PromotionRules {

    public boolean invokeRule(Double priceToReduce, Item item) {
        if (item.getOriginalPrice() != null) {
            if (priceToReduce < (.3 * item.getOriginalPrice())) {
                return true;
            } else {
                item.expirePromotion();
                return false;
            }
        } else {
            return true;
        }
    }
}
