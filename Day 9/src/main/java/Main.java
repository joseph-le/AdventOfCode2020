import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static final int preamble = 25;

    public static void main(String[] args) throws Exception {
//        File inputFile = new File("input-demo.txt");
        File inputFile = new File("input.txt");

        int togo = 0;
        List<Long> wholeList = new ArrayList<>();
        List<Long> previousList = new ArrayList<>();
        Set<Long> previousSet = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int count = 0;
            int removeIndex = 0;
            String line;
            boolean alreadyFound = false;
            Long theNumber = new Long(-1);
            while ((line = br.readLine()) != null) {
                count++;
//                System.out.println("" + count + " " + line);
                Long currentNumber = Long.parseLong(line);
                wholeList.add(currentNumber);
                if (previousList.size() < preamble) {
                    previousList.add(currentNumber);
                    previousSet.add(currentNumber);
                    continue;
                }
                boolean notSum = true;
                for (Long num: previousList) {
                    Long searchPair = currentNumber - num;
                    if (previousSet.contains(searchPair) && searchPair != num) {
                        notSum = false;
                        break;
                    }
                }
                if (!alreadyFound) {
                    if (notSum) {
                        alreadyFound = true;
                        theNumber = currentNumber;
                        System.out.println("Failed Property: " + currentNumber);
                    } else {
                        Long oldNumber = previousList.get(removeIndex);
                        previousList.set(removeIndex, currentNumber);
                        previousSet.remove(oldNumber);
                        previousSet.add(currentNumber);
                        removeIndex = (removeIndex + 1) % preamble;
                    }
                }
            }

            System.out.println("Number list size: " + wholeList.size());

            int start = -1;
            int end = -1;
            for (int i = 0; i < wholeList.size(); i++) {
                Long checkNumber = wholeList.get(i);
                int otherIndex = findOtherIndex(i, wholeList, theNumber - checkNumber);
                if (otherIndex != -1) {
                    start = i;
                    end = otherIndex;
                    break;
                }
            }

            Long smallest = wholeList.get(start);
            Long biggest = wholeList.get(start);
            for (int k = start; k <= end; k++) {
                Long curNum = wholeList.get(k);
                if (curNum < smallest) {
                    smallest = curNum;
                }
                if (curNum > biggest) {
                    biggest = curNum;
                }
//                System.out.println("curNum: " + curNum);
            }
            Long sum = smallest + biggest;
            System.out.println("Sum of smallest and largest: " + sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int findOtherIndex(int beginIndex, List<Long> wholeList, Long leftover) {
//        System.out.println("begin: " + beginIndex + ", leftover: " + leftover);
        if (leftover == 0) {
            return beginIndex;
        } else if (leftover < 0) {
            return -1;
        } else if (beginIndex + 1 < wholeList.size()) {
            Long checkNumber = wholeList.get(beginIndex + 1);
            return findOtherIndex(beginIndex + 1, wholeList, leftover - checkNumber);
        } else {
            System.out.println("Sad");
            return -1;
        }
    }
}
