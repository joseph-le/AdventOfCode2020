import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {

    static Map<String, Bag> bags = new HashMap<>();

    public static void main(String[] args) throws Exception {

        File inputFile = new File("input.txt");
//        File inputFile = new File("input-demo.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int count = 0;
            String line;
            while ((line = br.readLine()) != null) {
                count++;
                System.out.println("" + count + " " + line);
//                System.out.println(getParentBagName(line));
                Bag newBag = new Bag(getParentBagName(line));
                for (BagAmount ba : getChildrenBags(line)) {
                    newBag.addChildBag(ba.getName(), ba.getNumber());
                }
                bags.put(newBag.getName(), newBag);
            }

            boolean keepGoing = true;
            Set<String> bagsThatHaveGold = new HashSet<>();
            bagsThatHaveGold.add("shiny gold");

            for (String bagKey : bags.keySet()) {
                System.out.println(bags.get(bagKey));
            }

            do {
                keepGoing = false;
                for (String key : bags.keySet()) {
                    if (!bagsThatHaveGold.contains(key)) {
                        Bag unknownBag = bags.get(key);
                        for (String childKey : unknownBag.getChildrenBags().keySet()) {
                            if (bagsThatHaveGold.contains(childKey)) {
                                keepGoing = true;
                                bagsThatHaveGold.add(unknownBag.getName());
                                break;
                            }
                        }
                    }
                }
            } while (keepGoing);

            System.out.println("Number of bags that can contain shiny gold = " + (bagsThatHaveGold.size() - 1));

            long minBags = minimumBags(bags.get("shiny gold"));

            System.out.println("Number of minimum bags = " + (minBags - 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getParentBagName(String line) {
        String[] split = line.split("\\scontain\\s");
        return split[0].split("\\sbags")[0];
    }

    public static List<BagAmount> getChildrenBags(String line) {
        String[] split = line.split("\\scontain\\s");
        String strChildren = split[1];
        List<BagAmount> children = new ArrayList<>();
        String[] strBags = strChildren.split("[,.]");
        for (int i = 0; i < strBags.length; i++) {
            BagAmount ba = parseBag(strBags[i]);
            if (ba != null) {
                children.add(ba);
            }
        }
        System.out.println();
        return children;
    }

    private static BagAmount parseBag(String line) {
        line = StringUtils.trimToNull(line);
        if (line == null || line.isEmpty() || line.equalsIgnoreCase("no other bags")) {
            return null;
        }
        BagAmount newBA = new BagAmount();
        String[] noSpaces = line.split(" ");
        newBA.setNumber(Integer.parseInt(noSpaces[0]));
        String name = "";
        for (int i = 1; i < noSpaces.length - 1; i++) {
            name += noSpaces[i];
            if (i != noSpaces.length - 2) {
                name += " ";
            }
        }
        newBA.setName(name);
        return newBA;
    }

    public static long minimumBags(Bag currentBag) {
        long count = 1;

        for (String childKey : currentBag.getChildrenBags().keySet()) {
            count += minimumBags(bags.get(childKey)) * currentBag.getChildrenBags().get(childKey);
        }

        return count;
    }

    @Data
    public static class BagAmount {
        private int number;
        private String name;
    }

    @Data
    public static class Bag {
        private String name;
        private Map<String, Integer> childrenBags;

        Bag(String name) {
            this.name = name;
            this.childrenBags = new HashMap<>();
        }

        public void addChildBag(String name, int count) {
            Integer currentCount;
            if (this.childrenBags.containsKey(name)) {
                currentCount = this.childrenBags.get(name) + count;
            } else {
                currentCount = new Integer(count);
            }
            this.childrenBags.put(name, currentCount);
        }

        public void removeChildBag(String name) {
            this.childrenBags.remove(name);
        }

        public String toString() {
            return "{\n\tname: " + name + ",\n\tchildren: " + this.childrenBags + "\n}";
        }
    }
}
