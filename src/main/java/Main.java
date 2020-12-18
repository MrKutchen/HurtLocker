import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {

    public String readRawDataToString() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception {
        String data = (new Main()).readRawDataToString();
        Parser parser = new Parser();
        parser.createShoppingItems(data);
        System.out.println(parser.output());
        theOutputList(parser.output());
    }

    public static void theOutputList(String output) {
        try (PrintStream out = new PrintStream(new FileOutputStream("OutputList.txt"))) {
            out.print(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
