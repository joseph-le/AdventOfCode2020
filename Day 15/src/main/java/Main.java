import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static List<String> inputLines = new ArrayList<>();
    public static List<Integer> spoken = new ArrayList<>();
    public static Map<Integer, Spoken> map = new HashMap<>();

    public static void main(String[] args) throws Exception {
//        File inputFile = new File("input-demo.txt");
        File inputFile = new File("input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int count = 0;
            String line;
            while ((line = br.readLine()) != null) {
                count++;
                System.out.println("" + count + " " + line);
                inputLines.add(line);
            }
            String[] split = inputLines.get(0).split(",");
            int index = 1;
            spoken.add(-1);
            for (String numStr : split) {
                int number = Integer.parseInt(numStr);
                spoken.add(number);
                map.put(number, new Spoken(true, index, index));
                index++;
            }
            while (index <= 30000001) {
                Integer lastNumber = spoken.get(index - 1);
//                println("lastNumber = " + lastNumber);
                Spoken lastNumberSpoken = map.get(lastNumber);
                if (lastNumberSpoken.isFirstTime()) {
                    spoken.add(0);
                    map.get(0).updateSpoken(index);
                    map.get(0).setFirstTime(false);
//                    System.out.println(map.get(0));
                } else {
                    int difference = lastNumberSpoken.getDifference();
                    spoken.add(difference);
                    if (map.containsKey(difference)) {
                        Spoken updateSpoken = map.get(difference);
                        updateSpoken.updateSpoken(index);
                        updateSpoken.setFirstTime(false);
                        map.put(difference, updateSpoken);
                    } else {
                        map.put(difference, new Spoken(true, index, index));
                    }
                }

//                println("Spoken[" + index + "] = " + spoken.get(index));
                index++;
            }
            println("2020 = " + spoken.get(2020));
            println("30000000 = " + spoken.get(30000000));

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

    @Data
    @AllArgsConstructor
    public static class Spoken {

        private boolean firstTime;
        private int mostRecent;
        private int secondMostRecent;

        public int getDifference() {
            return mostRecent - secondMostRecent;
        }

        public void updateSpoken(int newIndex) {
            secondMostRecent = mostRecent;
            mostRecent = newIndex;
        }

        public String toString() {
            return "{\n\tfirstTime: " + firstTime + ",\n\tmostRecent: " + mostRecent + ",\n\tsecondMostRecent: " + secondMostRecent + "\n}";
        }
    }
}
