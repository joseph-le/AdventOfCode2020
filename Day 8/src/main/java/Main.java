import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        File inputFile = new File("input.txt");
//        File inputFile = new File("input-demo.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int count = 0;
            String line;

            List<Instruction> instructions = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                System.out.println("" + count + " " + line);
                Instruction newI = new Instruction();
                newI.setFullLine(line);
                String[] split = line.split(" ");
                newI.setOp(split[0]);
                newI.setValue(Integer.parseInt(split[1]));
                instructions.add(newI);
                count++;
            }
            int accumulator = checkLoop(instructions);
            System.out.println("value in accumulator: " + accumulator);

            String accumulation = "";
            for (int i = 0; i < instructions.size(); i++) {
                for (int k = 0; k < instructions.size(); k++) {
                    instructions.get(k).setVisited(0);
                }
                Instruction checkInstruction = instructions.get(i);
                switch (checkInstruction.getOp()) {
                    case "acc":
                        continue;
                    case "nop":
                        accumulation = checkLoopFix(instructions, i, "jmp");
//                        System.out.println("result: " + accumulation);
                        break;
                    case "jmp":
                        accumulation = checkLoopFix(instructions, i, "nop");
//                        System.out.println("result: " + accumulation);
                        break;
                }
                if (!"fuck".equalsIgnoreCase(accumulation)) {
                    System.out.println("result: " + accumulation);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int checkLoop(List<Instruction> instructions) {
        int i = 0;
        int accumulator = 0;
        while (true) {
            Instruction in = instructions.get(i);
            if (in.getVisited() > 0) {
                in.setVisited(0);
                break;
            }
            switch (in.getOp()) {
                case "acc":
                    accumulator += in.getValue();
                case "nop":
                    i++;
                    break;
                case "jmp":
                    i += in.getValue();
                    break;
            }
            if (i < 0) {
                i = instructions.size() + i;
            }
            in.setVisited(in.getVisited() + 1);
        }
        return accumulator;
    }

    public static String checkLoopFix(List<Instruction> instructions, int index, String newOp) {
//        System.out.println("index: " + index + ", newOp: " + newOp);
        int i = 0;
        int accumulator = 0;
        while (i < instructions.size()) {
            Instruction in = instructions.get(i);
            if (in.getVisited() > 0) {
                in.setVisited(0);
                return "fuck";
            }
            String op;
            if (newOp != null && i == index) {
                op = newOp;
//                System.out.println("newOp: " + newOp);
            } else {
                op = in.getOp();
            }
            switch (op) {
                case "acc":
                    accumulator += in.getValue();
                case "nop":
                    i++;
                    break;
                case "jmp":
                    i += in.getValue();
                    break;
            }
            if (i < 0) {
                i = instructions.size() + i;
            }
            in.setVisited(in.getVisited() + 1);
        }
        return "" + accumulator;
    }

    @Data
    public static class Instruction {
        private String fullLine;
        private String op;
        private int value;
        private int visited;

        Instruction() {
            this.visited = 0;
        }

        public String toString() {
            return "{ " + fullLine + ", " + visited + "}";
        }
    }
}
