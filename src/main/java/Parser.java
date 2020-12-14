import com.sun.xml.internal.bind.v2.TODO;

import javax.management.AttributeNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private List<ShoppingItem> shoppingList;
    private Map<String, Integer> numOfPrices;
    private Integer numOfErrors = 0;


    public Parser() {
        shoppingList = new ArrayList<>();
        numOfPrices = new LinkedHashMap<>();
    }

    public String[] splitRawData(String data) {
        Pattern pattern = Pattern.compile("##");
        String[] splitData = pattern.split(data);
        return splitData;
    }

    public String formattedShoppingItem(String data) throws IllegalArgumentException {
        if (Pattern.matches("[Mm][Ii][Ll][Kk]", data))
            return "Milk";
        else if (Pattern.matches("[Bb][Rr][Ee][Aa][Dd]", data))
            return "Bread";
        else if (Pattern.matches("[Cc][0Oo][0Oo][Kk][Ii][Ee][Ss]", data))
            return "Cookies";
        else if (Pattern.matches("[Aa][Pp][Pp][Ll][Ee][Ss]", data))
            return "Apples";
        else
            throw new IllegalArgumentException();
    }

    public String splitOnFieldsAndFind(String data, int index) throws AttributeNotFoundException {
        try {
            Pattern pattern = Pattern.compile("[\\w\\d.]+");
            Matcher matcher = pattern.matcher(splitRawData(data)[index]);
            if (matcher.find()) {
                return formattedShoppingItem(matcher.group());
            } else {
                numOfErrors++;
                throw new AttributeNotFoundException();
            }
        } catch (AttributeNotFoundException e) {
            System.err.println("Error" + e);
            return "Error";
        }
    }

    public void addShoppingItem(String name, String price, String type, String date) {
        shoppingList.add(new ShoppingItem(name, price, type, date));
    }

    public void createShoppingItems(String data) throws AttributeNotFoundException {
        String[] items = data.split("##");
        for (String item : items) {
            addShoppingItem(splitOnFieldsAndFind(item, 1), splitOnFieldsAndFind(item, 3), splitOnFieldsAndFind(item, 5), splitOnFieldsAndFind(item, 7));
        }
    }

    //TODO Build method to obtain numbers of each shopping item
    //TODO Create method to print shopping list items for each item
    //TODO Unit testing
}
