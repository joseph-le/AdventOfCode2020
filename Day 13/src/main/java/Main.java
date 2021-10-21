import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {
//        File inputFile = new File("input-demo.txt");
        File inputFile = new File("input.txt");
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int count = 0;
            String line;
            while ((line = br.readLine()) != null) {
                count++;
                println("" + count + " " + line);
                lines.add(line);
            }
            long currentTime = Long.parseLong(lines.get(0));
            List<Long> buses = parseBuses(lines.get(1));

            long nextBus = nextBus(currentTime, buses);
            long difference = nextBus * ((currentTime / nextBus) + 1) - currentTime;
            long result = nextBus * difference;
            println("id: " + nextBus + ", diff: " + difference + ", result: " + result);
            println("" + buses);

            List<Long> newBuses = newParseBuses(lines.get(1));
            long time = newBuses.get(0);
            long stepSize = newBuses.get(0);
            for (int i = 1; i < newBuses.size(); i++) {
                if (newBuses.get(i) == -1) {
                    continue;
                }
                while ((time + i) % newBuses.get(i) != 0) {
                    time += stepSize;
                }
                stepSize *= newBuses.get(i);
            }
            println("" + newBuses);
            println("blah " + time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Long nextBus(long time, List<Long> buses) {
        long id = buses.get(0);
        long difference = ((time / id) + 1) * id - time;
        for (Long bus : buses) {
            if (bus == -1) {
                continue;
            }
            long newDiff = ((time / bus) + 1) * bus - time;
            if (newDiff < difference) {
                id = bus;
                difference = newDiff;
            }
        }

        return (long) id;
    }

    public static List<Long> newParseBuses(String line) {
        List<Long> newBuses = new ArrayList<>();
        String[] split = line.split(",");
        for (String item: split) {
            if ("x".equals(item)) {
                newBuses.add(1L);
            } else {
                newBuses.add(Long.parseLong(item));
            }
        }
        return newBuses;
    }

    public static List<Long> parseBuses(String busline) {
        String[] busesStr = busline.split(",");
        List<Long> buses = new ArrayList<>();
        for (String newBus : busesStr) {
            if (newBus.matches("[\\d]+")) {
                buses.add(Long.parseLong(newBus));
            } else {
                buses.add(-1L);
            }
        }
        return buses;
    }

    public static void print(String line) {
        System.out.print(line);
    }

    public static void println(String line) {
        System.out.println(line);
    }
}
