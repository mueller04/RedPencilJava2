import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class RedPencilTest {
    PromotionRequestor promotionRequestor;
    PromotionManager promotionManager = new PromotionManager();
    ItemManager itemManager;
    Item item;

    @Before
    public void setUp() {
        item = new Item("pen", 5.00);
        itemManager = new ItemManager(item);
    }

    @Test
    public void promotionBeginsWhenReductionBetween5And30Percent(){
        //Arrange
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.30);

        //Assert
        assertEquals(true, itemManager.isPromotion());
    }

    @Test
    public void promotionDoesNotBeginWhenReductionisLessThan5Percent(){
        //Arrange
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(.24);

        //Assert
        assertEquals(false, itemManager.isPromotion());
    }

    @Test
    public void promotionDoesNotBeginWhenReductionisGreaterThan30Percent(){
        //Arrange
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.51);

        //Assert
        assertEquals(false, itemManager.isPromotion());
    }

    @Test
    public void priceReduceswhenrequestReducePriceCalledWithReductionLowerThanOriginalPrice(){
        //Arrange
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.20);

        //Assert
        assertEquals(3.8, itemManager.getPrice(), 0);
    }

    @Test
    public void priceNotReducedwhenrequestReducePriceCalledWithPriceHigherThanOriginalPrice(){
        //Arrange
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(5.01);

        //Assert
        assertEquals(5.00, itemManager.getPrice(), 0);
    }

    @Test
    public void priceReducedwhenRequestReducePriceCalledWithPriceHigherThan30PercentButAlsoLowerThanOriginalPrice(){
        //Arrange
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(2.00);

        //Assert
        assertEquals(3.00, itemManager.getPrice(), 0);
    }

    @Test
    public void aPromotionLasts30DaysMax(){
        //Arrange
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);
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
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);
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
        itemManager = createItemManager(15, 15, true, item);
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(.80);

        //Assert
        assertEquals(LocalDate.now().minusDays(15), itemManager.getPromotionDate());
        assertEquals(true, itemManager.isPromotion());
    }

    @Test
    public void beginpromotionDateBeginsOnTheDayAPromotionIsCreated(){
        //Arrange
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.30);

        //Assert
        assertEquals(LocalDate.now(), itemManager.getPromotionDate());
        assertEquals(true, itemManager.isPromotion());
    }

    @Test
    public void priceIncreaseEndsPromotion(){
        //Arrange
        itemManager = createItemManager(0, 0, true, item);
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);

        //Act
        itemManager.increasePrice(1.30);

        //Assert
        assertEquals(false, itemManager.isPromotion());
        assertEquals(null, itemManager.getOriginalPrice());
    }

    @Test
    public void priceIncreaseNullifiesOriginalPrice(){
        //Arrange
        itemManager = createItemManager(0, 0, true, item);
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);

        //Act
        itemManager.increasePrice(1.30);

        //Assert
        assertEquals(null, itemManager.getOriginalPrice());
    }

    @Test
    public void priceReducedMoreThan30PercentFromOriginalPricePercentEndsPromotion(){
        //Arrange
        itemManager = createItemManager(0, 0, true, item);
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);
        itemManager.calculatePrice(1.00, false);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.51);

        //Assert
        assertEquals(false, itemManager.isPromotion());
    }

    @Test
    public void last30DaysofStablePriceIntersectsWithLastPromotionRunNoNewPromotionBegins(){
        //Arrange
        itemManager = createItemManager(45, 20, false, item);
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.30);

        //Assert
        assertEquals(false, itemManager.isPromotion());
    }

    @Test
    public void last30DaysofStablePriceDoesNotIntersectsWithLastPromotionSoNewPromotionBegins(){
        //Arrange
        itemManager = createItemManager(62, 31, false, item);
        promotionRequestor = new PromotionRequestor(itemManager, promotionManager);

        //Act
        promotionRequestor.requestReducePriceAndBeginPromotion(1.30);

        //Assert
        assertEquals(true, itemManager.isPromotion());
    }



    //Helper Methods
    public ItemManager createItemManager(int beginPromoDateSubtractDays, int lastPriceChangeDateSubtractDays, boolean isPromotion, Item item) {

        ItemManager newItemManager = new ItemManager(item);
        LocalDate date = LocalDate.now();

        newItemManager.setPromotionBeginDate(date.minusDays(beginPromoDateSubtractDays));
        newItemManager.setLastPriceChangeDate(date.minusDays(lastPriceChangeDateSubtractDays));
        newItemManager.setIsPromotion(isPromotion);

        return newItemManager;

    }


}
