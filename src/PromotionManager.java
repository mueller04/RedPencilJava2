import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PromotionManager {

    ArrayList<PromotionRules> promotionRuleList;

    public PromotionManager() {
        promotionRuleList = new ArrayList<PromotionRules>();
        promotionRuleList.add(new IsPriceReductionLessThan30PercentofOriginalPriceDuringActivePromotion());
        promotionRuleList.add(new HasPriceBeenStable30Days());
        promotionRuleList.add(new IsPriceChangeIsWithinPercentages());
        promotionRuleList.add(new IsLast30DaysofStablePriceNotInteresectWithLastPromotionRun());
    }

    public boolean areCreatePromotionConditionsMet(Double priceToReduce,  Item item) {
        boolean returnFlag = true;

        for (PromotionRules promotionRules : promotionRuleList) {
            boolean failedRuleFlag;
            failedRuleFlag = promotionRules.invokeRule(priceToReduce, item);
            if (failedRuleFlag == false){
                returnFlag = failedRuleFlag;
            }
        }
        return returnFlag;
    }

    public boolean isPromotion(Item item){
        LocalDate now = LocalDate.now();
        if (item.getBeginPromoDate() != null ) {
            if (ChronoUnit.DAYS.between(item.getBeginPromoDate(), now) > 30) {
                item.setIsPromotion(false);
            }
        }
        return item.getIsPromotion();
    }


}
