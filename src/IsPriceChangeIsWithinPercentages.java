
public class IsPriceChangeIsWithinPercentages implements PromotionRules {

    public boolean invokeRule(Double price, Item item) {
        return (price > item.getPrice() * .05 && price < item.getPrice() * .3);
    }
}
