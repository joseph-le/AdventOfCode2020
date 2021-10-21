import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<String> inputLines = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        File inputFile = new File("input-demo.txt");
        // File inputFile = new File("input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int count = 0;
            String line;
            while ((line = br.readLine()) != null) {
                count++;
                System.out.println("" + count + " " + line);
                inputLines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void print(String line) {
        System.out.print(line);
    }

    public static void println(String line) {
        System.out.println(line);
    }
}


