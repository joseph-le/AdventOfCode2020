import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<String> rows = new ArrayList<>();
    public static List<String> newRows = new ArrayList<>();

    public static void main(String[] args) throws Exception {
//        File inputFile = new File("input-demo.txt");
         File inputFile = new File("input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int count = 0;
            String line;
            while ((line = br.readLine()) != null) {
//                count++;
//                System.out.println("" + count + " " + line);
                rows.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean changed = false;
//        do {
//            changed = processSeats();
//            rows = newRows;
//        } while (changed);
//
        int count = countSeats();
//        System.out.println("count = " + count);

        changed = false;
        do {
            changed = processSeats2();
            rows = newRows;
            for (String row: rows) {
                System.out.println(row);
            }
            System.out.println("-------------------");
        } while (changed);

        count = countSeats();
        System.out.println("count2 = " + count);
    }

    public static boolean processSeats() {
        boolean changed = false;
        newRows = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            String newRow = "";
            for (int k = 0; k < rows.get(i).length(); k++) {
                char spot = rows.get(i).charAt(k);
                switch (spot) {
                    case '.':
                        newRow += ".";
                        break;
                    case 'L':
                        if (numberOfOccupiedSeats(i, k) == 0) {
                            newRow += "#";
                            changed = true;
                        } else {
                            newRow += "L";
                        }
                        break;
                    case '#':
                        if (numberOfOccupiedSeats(i, k) >= 4) {
                            newRow += "L";
                            changed = true;
                        } else {
                            newRow += "#";
                        }
                        break;
                }
            }
            newRows.add(newRow);
        }
        return changed;
    }

    public static boolean processSeats2() {
        boolean changed = false;
        newRows = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            String newRow = "";
            for (int k = 0; k < rows.get(i).length(); k++) {
                char spot = rows.get(i).charAt(k);
                switch (spot) {
                    case '.':
                        newRow += ".";
                        break;
                    case 'L':
                        if (numberOfOccupiedSeats2(i, k) == 0) {
                            newRow += "#";
                            changed = true;
                        } else {
                            newRow += "L";
                        }
                        break;
                    case '#':
                        if (numberOfOccupiedSeats2(i, k) >= 5) {
                            newRow += "L";
                            changed = true;
                        } else {
                            newRow += "#";
                        }
                        break;
                }
            }
            newRows.add(newRow);
        }
        return changed;
    }

    public static int countSeats() {
        int count = 0;
        for (String row : rows) {
            for (int i = 0; i < row.length(); i++) {
                if (row.charAt(i) == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    public static int numberOfOccupiedSeats(int row, int column) {
        int count = 0;

        for (int i = row - 1; i < row + 2; i++) {
            for (int k = column - 1; k < column + 2; k++) {
                if (i < 0 || rows.size() <= i) {
                    continue;
                }
                if (k < 0 || rows.get(i).length() <= k) {
                    continue;
                }
                if (i == row && k == column) {
                    continue;
                }
                char checkSpot = rows.get(i).charAt(k);
                switch (checkSpot) {
                    case '.':
                    case 'L':
                        break;
                    case '#':
                        count++;
                        break;
                }
            }
        }
        return count;
    }

    public static int numberOfOccupiedSeats2(int row, int column) {
        int count = 0;
        // NW
        int y = row;
        int x = column;
        do {
            y--;
            x--;
            if (x < 0 || y < 0) {
                break;
            }
            if (rows.get(y).charAt(x) == 'L') {
                break;
            }
            if (rows.get(y).charAt(x) == '#') {
                count++;
                break;
            }
        } while (true);
        // N
        x = column;
        y = row;
        do {
            y--;
            if (y < 0) {
                break;
            }
            if (rows.get(y).charAt(x) == 'L') {
                break;
            }
            if (rows.get(y).charAt(x) == '#') {
                count++;
                break;
            }
        } while (true);
        // NE
        x = column;
        y = row;
        do {
            y--;
            x++;
            if (y < 0 || rows.get(y).length() <= x) {
                break;
            }
            if (rows.get(y).charAt(x) == 'L') {
                break;
            }
            if (rows.get(y).charAt(x) == '#') {
                count++;
                break;
            }
        } while (true);
        // W
        x = column;
        y = row;
        do {
            x--;
            if (x < 0) {
                break;
            }
            if (rows.get(y).charAt(x) == 'L') {
                break;
            }
            if (rows.get(y).charAt(x) == '#') {
                count++;
                break;
            }
        } while (true);
        // E
        x = column;
        y = row;
        do {
            x++;
            if (rows.get(y).length() <= x) {
                break;
            }
            if (rows.get(y).charAt(x) == 'L') {
                break;
            }
            if (rows.get(y).charAt(x) == '#') {
                count++;
                break;
            }
        } while (true);
        // SW
        x = column;
        y = row;
        do {
            x--;
            y++;
            if (x < 0 || y >= rows.size()) {
                break;
            }
            if (rows.get(y).charAt(x) == 'L') {
                break;
            }
            if (rows.get(y).charAt(x) == '#') {
                count++;
                break;
            }
        } while (true);
        // S
        x = column;
        y = row;
        do {
            y++;
            if (y >= rows.size()) {
                break;
            }
            if (rows.get(y).charAt(x) == 'L') {
                break;
            }
            if (rows.get(y).charAt(x) == '#') {
                count++;
                break;
            }
        } while (true);
        // SE
        x = column;
        y = row;
        do {
            x++;
            y++;
            if (y >= rows.size() || rows.get(y).length() <= x) {
                break;
            }
            if (rows.get(y).charAt(x) == 'L') {
                break;
            }
            if (rows.get(y).charAt(x) == '#') {
                count++;
                break;
            }
        } while (true);
        return count;
    }
}
