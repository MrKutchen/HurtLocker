import org.junit.Assert;
import org.junit.Test;

public class ShoppingItemTest {
    ShoppingItem shoppingItem = new ShoppingItem("Apples", "0.25", "Food", "08/24/20");

    @Test
    public void getName() {
        //Given
        String expected = "Apples";
        //When
        String actual = shoppingItem.getName();
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getPrice() {
        //Given
        String expected = "0.25";
        //When
        String actual = shoppingItem.getPrice();
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getType() {
        //Given
        String expected = "Food";
        //When
        String actual = shoppingItem.getType();
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getExpirationDate() {
        //Given
        String expected = "08/24/20";
        //When
        String actual = shoppingItem.getExpiration();
        //Then
        Assert.assertEquals(expected, actual);
    }
}
