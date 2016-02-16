import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.time.LocalDate;

public class RedPencilTest {
    PromotionRequestor promotionRequestor;
    PromotionManager promotionManager = new PromotionManager();
    ItemManager itemManager;
    Item item;

    @Before
    public void setUp() {
        item = new Item("pen", 5.00);
        itemManager = new ItemManager(item);
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);
    }

    @Test
    public void promotionBeginsWhenReductionBetween5And30Percent(){
        //Arrange

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.30);

        //Assert
        assertEquals(true, itemManager.isPromotion());
    }

    @Test
    public void promotionDoesNotBeginWhenReductionisLessThan5Percent(){
        //Arrange

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(.24);

        //Assert
        assertEquals(false, itemManager.isPromotion());
    }

    @Test
    public void promotionDoesNotBeginWhenReductionisGreaterThan30Percent(){
        //Arrange

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.51);

        //Assert
        assertEquals(false, itemManager.isPromotion());
    }

    @Test
    public void priceReduceswhenrequestReducePriceCalledWithReductionLowerThanOriginalPrice(){
        //Arrange

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.20);

        //Assert
        assertEquals(3.8, itemManager.getPrice(), 0);
    }

    @Test
    public void priceNotReducedwhenrequestReducePriceCalledWithPriceHigherThanOriginalPrice(){
        //Arrange

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(5.01);

        //Assert
        assertEquals(5.00, itemManager.getPrice(), 0);
    }

    @Test
    public void priceReducedwhenRequestReducePriceCalledWithPriceHigherThan30PercentButAlsoLowerThanOriginalPrice(){
        //Arrange

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(2.00);

        //Assert
        assertEquals(3.00, itemManager.getPrice(), 0);
    }

    @Test
    public void aPromotionLasts30DaysMax(){
        //Arrange
        LocalDate date = LocalDate.now();
        LocalDate beginPromoDate = date.minusDays(31);
        itemManager.setIsPromotion(true);

        //Act
        itemManager.setPromotionBeginDate(beginPromoDate);

        //Assert
        assertEquals(false, itemManager.isPromotion());
    }

    @Test
    public void priceHasNotBeenStable30DaysNoPromotionBegins(){
        //Arrange
        LocalDate lastPriceChangeDate = LocalDate.now().minusDays(15);
        itemManager.setLastPriceChangeDate(lastPriceChangeDate);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.00);

        //Assert
        assertEquals(4.00, itemManager.getPrice(), 0);
        assertEquals(false, itemManager.isPromotion());
    }

    @Test
    public void promotionBeginDateIsNotExtendedToCurrentDayIfPriceIsChangedAgainDuringAPromotion(){
        //Arrange
        LocalDate date = LocalDate.now();
        LocalDate beginPromoDate15DaysAgo = date.minusDays(15);
        itemManager.setLastPriceChangeDate(beginPromoDate15DaysAgo);
        itemManager.setPromotionBeginDate(beginPromoDate15DaysAgo);
        itemManager.setIsPromotion(true);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(.80);

        //Assert
        assertEquals(beginPromoDate15DaysAgo, itemManager.getPromotionDate());
        assertEquals(true, itemManager.isPromotion());
    }

    @Test
    public void beginpromotionDateBeginsOnTheDayAPromotionIsCreated(){
        //Arrange

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.30);

        //Assert
        assertEquals(LocalDate.now(), itemManager.getPromotionDate());
        assertEquals(true, itemManager.isPromotion());
    }

    @Test
    public void priceIncreaseEndsPromotion(){
        //Arrange
        itemManager.setLastPriceChangeDate(LocalDate.now());
        itemManager.setPromotionBeginDate(LocalDate.now());
        itemManager.setIsPromotion(true);

        //Act
        itemManager.increasePrice(1.30);

        //Assert
        assertEquals(false, itemManager.isPromotion());
        assertEquals(null, itemManager.getOriginalPrice());
    }

    @Test
    public void priceIncreaseNullifiesOriginalPrice(){
        //Arrange
        itemManager.setLastPriceChangeDate(LocalDate.now());
        itemManager.setPromotionBeginDate(LocalDate.now());
        itemManager.setIsPromotion(true);

        //Act
        itemManager.increasePrice(1.30);

        //Assert
        assertEquals(null, itemManager.getOriginalPrice());
    }

    @Test
    public void priceReducedMoreThan30PercentFromOriginalPricePercentEndsPromotion(){
        //Arrange
        itemManager.setLastPriceChangeDate(LocalDate.now());
        itemManager.setPromotionBeginDate(LocalDate.now());
        itemManager.setIsPromotion(true);
        itemManager.calculatePrice(1.00, false);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.51);

        //Assert
        assertEquals(false, itemManager.isPromotion());
    }

    @Test
    public void last30DaysofStablePriceIntersectsWithLastPromotionRunNoNewPromotionBegins(){
        //Arrange

        LocalDate date = LocalDate.now();
        LocalDate beginPromoDate45DaysAgo = date.minusDays(45);
        itemManager.setPromotionBeginDate(beginPromoDate45DaysAgo);
        itemManager.setLastPriceChangeDate(date.minusDays(31));

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.30);

        //Assert
        assertEquals(false, itemManager.isPromotion());
    }

    @Test
    public void last30DaysofStablePriceDoesNotIntersectsWithLastPromotionSoNewPromotionBegins(){
        //Arrange

        LocalDate date = LocalDate.now();
        LocalDate beginPromoDate45DaysAgo = date.minusDays(62);
        itemManager.setPromotionBeginDate(beginPromoDate45DaysAgo);
        itemManager.setLastPriceChangeDate(date.minusDays(31));

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.30);

        //Assert
        assertEquals(true, itemManager.isPromotion());
    }

}
