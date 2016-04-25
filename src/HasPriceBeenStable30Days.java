import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class HasPriceBeenStable30Days implements PromotionRules {

    public boolean invokeRule(Double priceToReduce, Item item) {
        if (item.getLastPriceChangeDate() != null ) {
            return (ChronoUnit.DAYS.between(item.getLastPriceChangeDate(), LocalDate.now()) > 30);
        } else {
            return true;
        }
    }
}
