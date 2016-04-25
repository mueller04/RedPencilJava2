import java.time.temporal.ChronoUnit;

public class IsLast30DaysofStablePriceNotInteresectWithLastPromotionRun implements PromotionRules {

    public boolean invokeRule(Double priceToReduce, Item item) {
        if (item.getLastPriceChangeDate() != null && item.getBeginPromoDate() != null) {
            return ((ChronoUnit.DAYS.between((item.getBeginPromoDate()), item.getLastPriceChangeDate()) > 30));
        } else {
            return true;
        }
    }
}
