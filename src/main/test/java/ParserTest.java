import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
    Parser parser = new Parser();
    String rawDataPartial = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016";
    String rawDataFull = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##" +
            "naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##" +
            "NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##" +
            "naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##" +
            "naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##" +
            "naMe:CoOkieS;price:2.25;type:Food*expiration:1/25/2016##" +
            "naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##" +
            "naMe:COOkieS;price:2.25;type:Food;expiration:1/25/2016##" +
            "NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##" +
            "naMe:MilK;price:1.23;type:Food!expiration:4/25/2016##" +
            "naMe:apPles;price:0.25;type:Food;expiration:1/23/2016##" +
            "naMe:apPles;price:0.23;type:Food;expiration:5/02/2016##" +
            "NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##" +
            "naMe:;price:3.23;type:Food;expiration:1/04/2016##" +
            "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##" +
            "naME:BreaD;price:1.23;type:Food@expiration:1/02/2016##" +
            "NAMe:BrEAD;price:1.23;type:Food@expiration:2/25/2016##" +
            "naMe:MiLK;priCe:;type:Food;expiration:1/11/2016##" +
            "naMe:Cookies;price:2.25;type:Food;expiration:1/25/2016##" +
            "naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##" +
            "naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##" +
            "naMe:COOkieS;Price:2.25;type:Food;expiration:1/25/2016##" +
            "NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##" +
            "naMe:MilK;priCe:;type:Food;expiration:4/25/2016##" +
            "naMe:apPles;prIce:0.25;type:Food;expiration:1/23/2016##" +
            "naMe:apPles;pRice:0.23;type:Food;expiration:5/02/2016##" +
            "NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##" +
            "naMe:;price:3.23;type:Food^expiration:1/04/2016##";

    @Test
    public void splitRawDataTest() {
        //Given
        String[] expected = {"naMe", "Milk", "price", "3.23", "type", "Food", "expiration", "1/25/2016"};
        //When
        String[] actual = parser.splitRawData(rawDataPartial);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void formattedShoppingItemMilkTest() {
        //Given
        String expected = "Milk";
        //When
        String actual = parser.formattedShoppingItem("MiLK");
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void formattedShoppingItemBreadTest() {
        //Given
        String expected = "Bread";
        //When
        String actual = parser.formattedShoppingItem("BrEAD");
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void formattedShoppingItemAppleCookieTest() {
        //Given
        String expected = "Cookies";
        //When
        String actual = parser.formattedShoppingItem("Co0kieS");
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void formattedShoppingItemAppleTest() {
        //Given
        String expected = "Apples";
        //When
        String actual = parser.formattedShoppingItem("apPles");
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void splitOnFieldsAndFindTest() {
        //Given
        String expected = "Milk";
        //When
        String actual = parser.splitOnFieldsAndFind(rawDataPartial, 1);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addShoppingItemTest() {
        //Given
        int expectedSize = 1;
        //When
        parser.addShoppingItem("Milk", "3.45");
        int actualSize = parser.getShoppingList().size();
        //Then
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void createShoppingItemsTest() {
        //Given
        String expected = "Milk";
        //When
        parser.createShoppingItems("naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##" +
                "naME:BreaD;price:1.23;type:Food;expiration:1/02/2016");
        String actual = parser.getShoppingList().get(0).getName();
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shoppingItemsAndNumOfPricesInRawDataTest() {
        //Given
        parser.createShoppingItems(rawDataFull);
        String expected = "name:\tApples\t\tseen: 4 times\n" +
                "=============\t\t=============\n" +
                "Price:\t0.25\t\tseen: 2 times\n" +
                "-------------\t\t-------------\n" +
                "Price:\t0.23\t\tseen: 2 times";
        //When
        String actual = parser.shoppingItemsAndNumOfPricesInRawData("Apples");
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void output() {
        //Given
        parser.createShoppingItems(rawDataFull);
        String expected = "name:\tMilk\t\tseen: 6 times\n" +
                "=============\t\t=============\n" +
                "Price:\t3.23\t\tseen: 5 times\n" +
                "-------------\t\t-------------\n" +
                "Price:\t1.23\t\tseen: 1 times\n" +
                "\n" +
                "name:\tBread\t\tseen: 6 times\n" +
                "=============\t\t=============\n" +
                "Price:\t1.23\t\tseen: 6 times\n" +
                "-------------\t\t-------------\n" +
                "\n" +
                "name:\tCookies\t\tseen: 8 times\n" +
                "=============\t\t=============\n" +
                "Price:\t2.25\t\tseen: 8 times\n" +
                "-------------\t\t-------------\n" +
                "\n" +
                "name:\tApples\t\tseen: 4 times\n" +
                "=============\t\t=============\n" +
                "Price:\t0.25\t\tseen: 2 times\n" +
                "-------------\t\t-------------\n" +
                "Price:\t0.23\t\tseen: 2 times\n" +
                "\n" +
                "Errors\t\t\t\tseen: 4 times";
        //When
        String actual = parser.output();
        //Then
        Assert.assertEquals(expected, actual);
    }
}
