import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static final int maxRow = 128;
    public static final int maxColumn = 8;

    public static void main(String[] args) throws Exception {
        File inputFile = new File("input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            List<BoardingPass> boardingPasses = new ArrayList<>();
            String line;
            long max = 0;
            while ((line = br.readLine()) != null) {
//                System.out.println(line);
                BoardingPass newPass = new BoardingPass(line);
//                System.out.println(newPass);
                boardingPasses.add(newPass);
                if (newPass.getId() > max) {
                    max = newPass.getId();
                }
            }
            boardingPasses.sort(new Comparator<BoardingPass>() {
                @Override
                public int compare(BoardingPass o1, BoardingPass o2) {
                    return (int) (o1.getId() - o2.getId());
                }
            });
            for (int i = 0; i <= 127; i++) {
                for (int k = 0; k <= 7; k++) {
                    int id = i * 8 + k;
                    if (!boardingPasses.stream().anyMatch(b -> b.getId() == id)) {
                        System.out.println(id);
                    }
                }
            }

            System.out.println("max = " + max);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class BoardingPass {
        private String ticket;
        private int row;
        private int column;
        private long id;

        BoardingPass(String ticket) {
            this.ticket = ticket;
            calculateRow();
            calculateColumn();
            calculateID();
        }

        private void calculateRow() {
            Range range = new Range(0, 127);
            for (int i = 0; i < 7; i++) {
                char blah = ticket.charAt(i);
                int middle = (range.getMax() - range.getMin()) / 2 + range.getMin();
                if (blah == 'F') {
                    range.setMax(middle);
                } else if (blah == 'B') {
                    middle += 1;
                    range.setMin(middle);
                }
            }
            this.row = range.getMin();
        }

        private void calculateColumn() {
            Range range = new Range(0, 7);
            for (int i = 7; i < ticket.length(); i++) {
                char blah = ticket.charAt(i);
                int middle = (range.getMax() - range.getMin()) / 2 + range.getMin();
                if (blah == 'L') {
                    range.setMax(middle);
                } else if (blah == 'R') {
                    middle += 1;
                    range.setMin(middle);
                }
            }
            this.column = range.getMin();
        }


        private void calculateID() {
            this.id = this.row * 8 + this.column;
        }

        public int compareTo(BoardingPass other) {
            return (int) (other.getId() - this.id);
        }

        public String toString() {
            return "Ticket: " + ticket + ", Row: " + this.row + ", Column: " + this.column;
        }
    }

    @Data
    @AllArgsConstructor
    public static class Range {
        private int min;
        private int max;

        public String toString() {
            return "min: " + this.min + ", max: " + this.max;
        }
    }

}
