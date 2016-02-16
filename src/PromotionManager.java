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

    public boolean areCreatePromotionConditionsMet(Double price,  Item item) {
        boolean returnFlag = true;

        for (PromotionRules promotionRules : promotionRuleList) {
            boolean failedRuleFlag;
            failedRuleFlag = promotionRules.invokeRule(price, item);
            if (failedRuleFlag == false){
                returnFlag = failedRuleFlag;
            }
        }
        return returnFlag;
    }



}
