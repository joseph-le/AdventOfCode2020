import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws Exception {
        File inputFile = new File("input.txt");
        List<TreePattern> treePatterns = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                treePatterns.add(new TreePattern(line));
                System.out.println(line);
            }
            long count1 = countTrees(1, 1, treePatterns);
            long count2 = countTrees(3, 1, treePatterns);
            long count3 = countTrees(5, 1, treePatterns);
            long count4 = countTrees(7, 1, treePatterns);
            long count5 = countTrees(1, 2, treePatterns);

            System.out.println("count1 = " + count1);
            System.out.println("count2 = " + count2);
            System.out.println("count3 = " + count3);
            System.out.println("count4 = " + count4);
            System.out.println("count5 = " + count5);
            long result = count1 * count2 * count3 * count4 * count5;
            System.out.println("countMulti = " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long countTrees(int left, int down, List<TreePattern> treePatterns) {
        int leftIndex = 0;
        int treeCount = 0;
        for (int downIndex = down; downIndex < treePatterns.size(); downIndex += down) {
            leftIndex += left;
            TreePattern pattern = treePatterns.get(downIndex);
            if (pattern.isTree(leftIndex)) {
                treeCount++;
            }
        }
        return treeCount;
    }

    @Data
    @AllArgsConstructor
    public static class TreePattern {
        private String pattern;

        public boolean isTree(int position) {
            int positionWrap = position % pattern.length();
            return (pattern.charAt(positionWrap) == '#');
        }

        public long countTreesFirstPattern() {
            return pattern.chars().filter(ch -> ch == '#').count();
        }
    }

}
