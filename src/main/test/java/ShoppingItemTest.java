import org.junit.Assert;
import org.junit.Test;

public class ShoppingItemTest {
    ShoppingItem shoppingItem = new ShoppingItem("Apples", "0.25");

    @Test
    public void getNameTest() {
        //Given
        String expected = "Apples";
        //When
        String actual = shoppingItem.getName();
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getPriceTest() {
        //Given
        String expected = "0.25";
        //When
        String actual = shoppingItem.getPrice();
        //Then
        Assert.assertEquals(expected, actual);
    }

}
