import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private final ArrayList<ShoppingItem> shoppingList = new ArrayList<>();
    private int errors = 0;


    public String[] splitRawData(String string) {
        return string.split("([;:^@%*!])");
    }

    public String formattedShoppingItem(String data) {
        if (Pattern.matches("[Mm][Ii][Ll][Kk]", data))
            return "Milk";
        else if (Pattern.matches("[Bb][Rr][Ee][Aa][Dd]", data))
            return "Bread";
        else if (Pattern.matches("[Cc][0Oo][0Oo][Kk][Ii][Ee][Ss]", data))
            return "Cookies";
        else if (Pattern.matches("[Aa][Pp][Pp][Ll][Ee][Ss]", data))
            return "Apples";
        else {
            return data;
        }
    }

    public String splitOnFieldsAndFind(String string, int index) {
        Pattern fieldName = Pattern.compile("([\\w\\d.]+)");
        try {
            Matcher matcher = fieldName.matcher((splitRawData(string)[index]));
            if (matcher.find()) {
                return formattedShoppingItem(matcher.group());
            } else {
                errors++;
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return "Error";
        }
    }

    public void addShoppingItem(String name, String price, String type, String date) {
        shoppingList.add(new ShoppingItem(name, price, type, date));
    }

    public void createShoppingItem(String input) {
        String[] itemArray = input.split("##");
        for (String s : itemArray) {
            addShoppingItem(splitOnFieldsAndFind(s, 1), splitOnFieldsAndFind(s, 3), splitOnFieldsAndFind(s, 5), splitOnFieldsAndFind(s, 7));
        }
    }

    public String numOfShoppingItemsAndPriceInRawData(String string) {
        StringBuilder items = new StringBuilder();
        LinkedHashMap<String, Integer> priceCounter = new LinkedHashMap<>();
        int stringCount = 0;
        for (ShoppingItem item : shoppingList) {
            if (item.getName().equals(string)) {
                stringCount++;
                if (priceCounter.containsKey(item.getPrice())) {
                    priceCounter.put(item.getPrice(), priceCounter.get(item.getPrice()) + 1);
                } else if (item.getPrice().equals("Error")) {
                    stringCount--;
                } else {
                    priceCounter.put(item.getPrice(), 1);
                }
            }
        }
        return getOutput(string, items, priceCounter, stringCount);
    }

    private String getOutput(String string, StringBuilder items, LinkedHashMap<String, Integer> priceCounter, int stringCount) {
        items.append("name:\t").append(string).append("\t\t").append("seen: ").append(stringCount).append(" times\n");
        items.append("=============\t\t=============\n");
        int counter = 0;
        for (String key : priceCounter.keySet()) {
            items.append("Price:\t").append(key).append("\t\t").append("seen: ").append(priceCounter.get(key)).append(" times");
            if (counter == 0) {
                items.append("\n-------------\t\t-------------\n");
            }
            counter++;
        }

        return items.toString();
    }

    public String output() {
        return numOfShoppingItemsAndPriceInRawData("Milk") +
                "\n\n" +
                numOfShoppingItemsAndPriceInRawData("Bread") +
                "\n" +
                numOfShoppingItemsAndPriceInRawData("Cookies") +
                "\n" +
                numOfShoppingItemsAndPriceInRawData("Apples") +
                "\n\n" +
                "Errors\t\t\t\tseen: " + errors + " times";
    }

}
