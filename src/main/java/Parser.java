import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private final ArrayList<ShoppingItem> shoppingList = new ArrayList<>();
    private int errors = 0;


    public String[] splitRawData(String data) {
        return data.split("([;:^@%*!])");
    }

    public String formattedShoppingItem(String name) {
        if (Pattern.matches("[Mm][Ii][Ll][Kk]", name))
            return "Milk";
        else if (Pattern.matches("[Bb][Rr][Ee][Aa][Dd]", name))
            return "Bread";
        else if (Pattern.matches("[Cc][0Oo][0Oo][Kk][Ii][Ee][Ss]", name))
            return "Cookies";
        else if (Pattern.matches("[Aa][Pp][Pp][Ll][Ee][Ss]", name))
            return "Apples";
        else {
            return name;
        }
    }

    public String splitOnFieldsAndFind(String data, int index) {
        Pattern fieldName = Pattern.compile("([\\w\\d.]+)");
        try {
            Matcher matcher = fieldName.matcher((splitRawData(data)[index]));
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

    public void addShoppingItem(String name, String price) {
        shoppingList.add(new ShoppingItem(name, price));
    }

    public void createShoppingItems(String data) {
        String[] itemArray = data.split("##");
        for (String s : itemArray) {
            addShoppingItem(splitOnFieldsAndFind(s, 1), splitOnFieldsAndFind(s, 3));
        }
    }

    public String shoppingItemsAndNumOfPricesInRawData(String name) {
        LinkedHashMap<String, Integer> numOfPriceValues = new LinkedHashMap<>();
        int count = 0;
        for (ShoppingItem item : shoppingList) {
            if (item.getName().equals(name) && !item.getPrice().equals("Error")) {
                count++;
                if (numOfPriceValues.containsKey(item.getPrice())) {
                    numOfPriceValues.put(item.getPrice(), numOfPriceValues.get(item.getPrice()) + 1);
                } else {
                    numOfPriceValues.put(item.getPrice(), 1);
                }
            }
        }
        return getOutput(name, numOfPriceValues, count);
    }

    public String getOutput(String name, LinkedHashMap<String, Integer> numOfPriceValues, int count) {
        StringBuilder output = new StringBuilder();
        output.append("name:\t").append(name).append("\t\t").append("seen: ").append(count).append(" times\n");
        output.append("=============\t\t=============\n");
        int counter = 0;
        for (String key : numOfPriceValues.keySet()) {
            output.append("Price:\t").append(key).append("\t\t").append("seen: ").append(numOfPriceValues.get(key)).append(" times");
            if (counter == 0) {
                output.append("\n-------------\t\t-------------\n");
            }
            counter++;
        }

        return output.toString();
    }

    public String output() {
        return shoppingItemsAndNumOfPricesInRawData("Milk") +
                "\n\n" +
                shoppingItemsAndNumOfPricesInRawData("Bread") +
                "\n" +
                shoppingItemsAndNumOfPricesInRawData("Cookies") +
                "\n" +
                shoppingItemsAndNumOfPricesInRawData("Apples") +
                "\n\n" +
                "Errors\t\t\t\tseen: " + errors + " times";
    }

    public ArrayList<ShoppingItem> getShoppingList() {
        return shoppingList;
    }
}
