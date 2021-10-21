import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static List<String> inputLines = new ArrayList<>();

    public static List<Field> fields = new ArrayList<>();
    public static List<Integer> yourTicket = new ArrayList<>();
    public static List<List<Integer>> nearbyTickets = new ArrayList<>();
    public static List<List<Integer>> validTickets = new ArrayList<>();

    public static void main(String[] args) throws Exception {
//        File inputFile = new File("input-demo.txt");
        File inputFile = new File("input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int count = 0;
            String line;
            int mode = 0;
            while ((line = br.readLine()) != null) {
                count++;
//                System.out.println("" + count + " " + line);
                inputLines.add(line);
                if (StringUtils.isEmpty(line) || line.contains("ticket")) {
                    mode++;
                    continue;
                }
                switch (mode) {
                    case 0:
                        addNewField(line);
                        break;
                    case 2:
                        addYourTicket(line);
                        break;
                    case 4:
                        addNearbyTicket(line);
                        break;
                }
            }

//            System.out.println("fields " + fields);
//            System.out.println("your ticket " + yourTicket);
//            System.out.println("nearbyTickets " + nearbyTickets);

            List<Integer> invalidNumbers = new ArrayList<>();
            for (List<Integer> ticket : nearbyTickets) {
                boolean valid = true;
                for (Integer value : ticket) {
                    if (!checkInRanges(value)) {
                        invalidNumbers.add(value);
                        valid = false;
                    }
                }
                if (valid) {
                    validTickets.add(ticket);
                }
            }
            int sum = invalidNumbers.stream().reduce(0, Integer::sum);
            System.out.println("Error rate: " + sum);

            List<Set<String>> fieldSudoku = new ArrayList(fields.size());
            for (int k = 0; k < fields.size(); k++) {
                fieldSudoku.add(new HashSet<>());
                for (Field f : fields) {
                    fieldSudoku.get(k).add(f.getName());
                }
            }

            System.out.println("validTickets size: " + validTickets.size());

            // write small numbers in sudoku box
            for (Field f : fields) {
                for (List<Integer> ticket : validTickets) {
                    for (int i = 0; i < ticket.size(); i++) {
                        Integer value = ticket.get(i);
                        if (!f.isInRange(value)) {
                            fieldSudoku.get(i).remove(f.getName());
                        }
                    }
                }
            }
            System.out.println("fieldSudoku: " + fieldSudoku);

            for (Set<String> x : fieldSudoku) {
                System.out.println(x);
            }

            boolean finished = true;

            // erase numbers from sudoku box
            do {
                finished = true;
                for (int p = 0; p < fieldSudoku.size(); p++) {
                    Set<String> s = fieldSudoku.get(p);
                    if (s.size() == 1) {
                        String blah = s.stream().collect(Collectors.toList()).get(0);
                        for (int l = 0; l < fieldSudoku.size(); l++) {
                            if (p == l) {
                                continue;
                            } else {
                                fieldSudoku.get(l).remove(blah);
                            }
                        }
                    } else {
                        finished = false;
                    }
                }
            } while (!finished);

            System.out.println("fieldSudoku 2: " + fieldSudoku);

            List<String> fieldMapping = fieldSudoku.stream().map(s -> s.stream().collect(Collectors.toList()).get(0)).collect(Collectors.toList());
            System.out.println("Field Mapping: " + fieldMapping);

            long result = 1L;
            for (int a = 0; a < fieldMapping.size(); a++) {
                String fieldName = fieldMapping.get(a);
                if (fieldName.startsWith("departure")) {
                    result *= yourTicket.get(a);
                }
            }

            System.out.println("result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkInRanges(Integer value) {
        for (Field f : fields) {
            if (f.isInRange(value)) {
                return true;
            }
        }
        return false;
    }

    public static void addNewField(String line) {
        String[] split1 = line.split(": ");
        Field newField = new Field();
        newField.setName(split1[0]);
        String[] split2 = split1[1].split(" or ");
        String[] split3 = split2[0].split("-");
        newField.setStartOne(Integer.parseInt(split3[0]));
        newField.setEndOne(Integer.parseInt(split3[1]));
        String[] split4 = split2[1].split("-");
        newField.setStartTwo(Integer.parseInt(split4[0]));
        newField.setEndTwo(Integer.parseInt(split4[1]));

        fields.add(newField);
    }

    public static void addYourTicket(String line) {
        String[] split = line.split(",");
        for (String s : split) {
            yourTicket.add(Integer.parseInt(s));
        }
    }

    public static void print(String line) {
        System.out.print(line);
    }

    public static void println(String line) {
        System.out.println(line);
    }

    public static void addNearbyTicket(String line) {
        String[] split = line.split(",");
        List<Integer> nearbyTicket = new ArrayList<>();
        for (String s : split) {
            nearbyTicket.add(Integer.parseInt(s));
        }
        nearbyTickets.add(nearbyTicket);
    }

    @Data
    public static class Field {
        private String name;
        private int startOne;
        private int endOne;
        private int startTwo;
        private int endTwo;

        public boolean isInRange(int number) {
            if (startOne <= number && number <= endOne) {
                return true;
            }
            if (startTwo <= number && number <= endTwo) {
                return true;
            }

            return false;
        }

        public String toString() {
            return "{\n\tname: " + name + ",\n\tstartOne: " + startOne + ",\n\tendOne: " + endOne + ",\n\tstartTwo: " + startTwo + ",\n\tendTwo: " + endTwo + "\n}";
        }
    }
}
