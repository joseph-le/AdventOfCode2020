import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static List<String> lines = new ArrayList<>();
    public static Map<String, String> map = new HashMap<>();
    public static Map<String, String> map2 = new HashMap<>();

    public static void main(String[] args) throws Exception {
//        File inputFile = new File("input-demo.txt");
         File inputFile = new File("input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int count = 0;
            String line;
            while ((line = br.readLine()) != null) {
                count++;
//                System.out.println("" + count + " " + line);
                lines.add(line);

                String[] split = line.split(" = ");
                if ("mask".equals(split[0])) {
                    map.put(split[0], split[1]);
                } else {
                    map.put(split[0], applyMask(split[1], map.get("mask")));
                }
            }

            long sum = 0L;
            for (String key : map.keySet()) {
                if ("mask".equals(key)) {
                    continue;
                }
                String binaryString = map.get(key);
                Long value = Long.parseLong(binaryString, 2);
                sum += value;
            }

            println("Sum: " + sum);
            // Part 2

            for (String inputLine : lines) {
//                println("blah " + inputLine);
                String[] split = inputLine.split(" = ");
                if ("mask".equals(split[0])) {
                    map2.put(split[0], split[1]);
                } else {
                    updateMemory(split[0].replaceAll("\\D", ""), split[1]);
                }
            }

            long sum2 = 0L;
            for (String key : map2.keySet()) {
                if ("mask".equals(key)) {
                    continue;
                }
                String binaryString = map2.get(key);
                Long value = Long.parseLong(binaryString);
                sum2 += value;
            }

            println("Sum2: " + sum2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String applyMask(String decimalString, String mask) {
        long decimalValue = Long.parseLong(decimalString);
        String binaryString = Long.toBinaryString(decimalValue);
        binaryString = padLeftZeroes(binaryString, mask.length());
        String maskedString = "";
        for (int i = 0; i < mask.length(); i++) {
            char spot = mask.charAt(i);
            switch (spot) {
                case '1':
                case '0':
                    maskedString = maskedString + spot;
                    break;
                case 'X':
                    maskedString = maskedString + binaryString.charAt(i);
                    break;
            }
        }
        return maskedString;
    }

    public static String applyMaskAddress(String binaryString, String mask) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mask.length(); i++) {
            char spot = mask.charAt(i);
            switch(spot) {
                case '0':
                    sb.append(binaryString.charAt(i));
                    break;
                case '1':
                case 'X':
                    sb.append(spot);
                    break;
            }
        }
        return sb.toString();
    }

    public static void updateMemory(String address, String valueDecimalString) {
        println("address: " + address + ", decimalString: " + valueDecimalString);
        String addressBinaryString = padLeftZeroes(Long.toBinaryString(Long.parseLong(address)), 36);
//        println("addressBinaryString: " + addressBinaryString);

        // mask address
        String addressMaskedResult = applyMaskAddress(addressBinaryString, map2.get("mask"));
//        println("addressMaskedResult: " + addressMaskedResult);

        int numberOfXs = countXs(map2.get("mask"));
        List<String> newAddressesBinary = new ArrayList<>();
        for (int i = 0; i < (int) Math.pow(2, numberOfXs); i++) {
            String iBinary = padLeftZeroes(Integer.toBinaryString(i), numberOfXs);
            String workingAddress = addressMaskedResult;
//            println(iBinary);
            for (int j = 0; j < iBinary.length(); j++) {
                int xIndex = workingAddress.indexOf('X');
                workingAddress = workingAddress.substring(0, xIndex) + iBinary.charAt(j) + workingAddress.substring(xIndex + 1);
            }
            newAddressesBinary.add(workingAddress);
        }
        for (String addr: newAddressesBinary) {
            map2.put(addr, valueDecimalString);
        }
    }

    public static int countXs(String blah) {
        int count = 0;
        for (char spot: blah.toCharArray()) {
            if (spot == 'X') {
                count++;
            }
        }
        return count;
    }

    public static String padLeftZeroes(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }

    public static void print(String line) {
        System.out.print(line);
    }

    public static void println(String line) {
        System.out.println(line);
    }
}
