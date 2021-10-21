import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main {

    public static PasswordPolicy getPolicy(String policyString) {
        String[] spaceSplit = policyString.split(" ");
        String[] numbers = spaceSplit[0].split("-");
        String characterStr = spaceSplit[1];

        return new PasswordPolicy(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), characterStr.charAt(0));
    }

    public static void main(String[] args) throws Exception {
        File inputFile = new File("input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            int validCount = 0;
            int validCount2 = 0;
            while ((line = br.readLine()) != null) {
                String[] splitString = line.split(":");
                PasswordPolicy passwordPolicy = getPolicy(splitString[0]);
                String password = StringUtils.trim(splitString[1]);
                validCount = passwordPolicy.isValidPassword(password) ? validCount + 1 : validCount;
                validCount2 = passwordPolicy.isValidPassword2(password) ? validCount2 + 1 :validCount2;
            }
            System.out.println("count = " + validCount);
            System.out.println("count2 = " + validCount2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    @AllArgsConstructor
    private static class PasswordPolicy {
        private int minLetter;
        private int maxLetter;
        private char letter;

        public boolean isValidPassword(String password) {
            int countLetter = 0;
            for (char c: password.toCharArray()) {
                if (c == letter) {
                    countLetter++;
                }
            }
            if (minLetter <= countLetter && countLetter <= maxLetter) {
                return true;
            }
            return false;
        }

        public boolean isValidPassword2(String password) {
            int countLetter = 0;
            if (password.charAt(minLetter - 1) == letter) {
                countLetter++;
            }
            if (password.charAt(maxLetter - 1) == letter) {
                countLetter++;
            }

            if (countLetter == 1) {
                return true;
            }
            return false;
        }
    }
}
