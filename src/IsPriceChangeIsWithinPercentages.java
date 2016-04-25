
public class IsPriceChangeIsWithinPercentages implements PromotionRules {

    public boolean invokeRule(Double priceToReduce, Item item) {
        return (priceToReduce > item.getPrice() * .05 && priceToReduce < item.getPrice() * .3);
    }
}
