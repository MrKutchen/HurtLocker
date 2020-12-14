import org.apache.commons.io.IOUtils;

public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String input = (new Main()).readRawDataToString();
        Parser parser = new Parser();
        parser.createShoppingItem(input);
        System.out.println(parser.output());
    }
}
