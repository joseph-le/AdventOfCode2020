import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static List<Integer> adapterList = new ArrayList<>();
    public static List<Integer> differences = new ArrayList<>();
    public static Map<Integer, Long> remember = new HashMap<>();

    public static void main(String[] args) throws Exception {
//        File inputFile = new File("input-demo.txt");
         File inputFile = new File("input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int count = 0;
            String line;
            while ((line = br.readLine()) != null) {
                count++;
                Integer newAdapter = Integer.parseInt(line);
                adapterList.add(newAdapter);
            }
            Collections.sort(adapterList);
//            System.out.println(adapterList + " len: " + adapterList.size());

//            for (int i = 0; i < adapterList.size() - 1; i++) {
//                Integer currentNum = adapterList.get(i);
//                Integer nextNum = adapterList.get(i + 1);
//                differences.add(nextNum - currentNum);
//            }

//            System.out.println(differences + " length: " + differences.size());

            Map<Integer, Long> paths = new HashMap<>();
            paths.put(new Integer(0), 1L);

            adapterList.add(0, 0);
            adapterList.add(adapterList.get(adapterList.size() - 1) + 3);



            for (Integer adapter : adapterList) {
                if (!paths.containsKey(adapter)) {
                    paths.put(adapter, 0L);
                }
                for (int diff = 1; diff < 4; diff++) {
                    Integer nextAdapter = adapter + diff;
                    paths.putIfAbsent(nextAdapter, 0L);
                    if (adapterList.contains(nextAdapter)) {
                        paths.put(nextAdapter, paths.get(nextAdapter) + paths.get(adapter));
                    }
//                    System.out.println("nextAdapter: " + nextAdapter + " paths:" + paths);
                }
            }

            System.out.println("Distinct: " + paths.get(adapterList.get(adapterList.size() - 1)));

            Long totalRecursion = count(adapterList, 0);
            System.out.println("totalRecursion: " + totalRecursion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Long count(List<Integer> adapterList, int index) {
        if (remember.containsKey(index)) {
            return remember.get(index);
        }
        if (index == adapterList.size() - 1) {
            return 1L;
        }

        Long total = 0L;

        if (index + 1 < adapterList.size() && (adapterList.get(index + 1)  - adapterList.get(index) <= 3)) {
            total += count(adapterList, index + 1);
        }
        if (index + 2 < adapterList.size() && (adapterList.get(index + 2)  - adapterList.get(index) <= 3)) {
            total += count(adapterList, index + 2);
        }
        if (index + 3 < adapterList.size() && (adapterList.get(index + 3)  - adapterList.get(index) <= 3)) {
            total += count(adapterList, index + 3);
        }

        remember.put(index, total);
        return total;
    }
}
