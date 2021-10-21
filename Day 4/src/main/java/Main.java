import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        File inputFile = new File("input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            List<Passport> passports = new ArrayList<>();

            Passport currentPassport = new Passport();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (line.isEmpty()) {
                    passports.add(currentPassport);
                    currentPassport = new Passport();
                } else {
                    currentPassport.getFields().addAll(addFields(line));
                }
            }
            for (Passport p: passports) {
                System.out.println(p.toString());
            }
            System.out.println(passports.size());
            long validPassports = passports.stream().filter(p -> p.isValidPassport()).count();
            System.out.println("valid passports = " + validPassports);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Field> addFields(String fieldsString) {
        List<Field> results = new ArrayList<>();
        String[] fieldsArray = fieldsString.split(" ");
        for (int i = 0; i < fieldsArray.length; i++) {
            String[] fieldStuff = fieldsArray[i].split(":");
            results.add(new Field(fieldStuff[0], fieldStuff[1]));
        }
        return results;
    }

    @Data
    public static class Passport {
        private List<Field> fields = new ArrayList<>();

        public boolean isValidPassport() {
            int validCount = 0;
            for (Field f: fields) {
                if (f.isValidField()) {
                    validCount++;
                }
            }
            return (validCount == Field.validFields.size());
        }

        public String toString() {
            String resultString = "{ ";
            for (Field f: fields) {
                resultString += "'" + f.getFieldName() + "' : '" + f.getFieldValue() + "', ";
            }
            resultString += "}";
            return resultString;
        }
    }

    @Data
    @AllArgsConstructor
    public static class Field {

        public static List<String> validFields = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
        public static List<String> validEyeColors = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

        private String fieldName;
        private String fieldValue;

        public boolean isValidField() {
            switch (fieldName) {
                case "byr":
                    try {
                        int number = Integer.parseInt(fieldValue);
                        if (number < 1920 || 2002 < number)  {
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    break;
                case "iyr":
                    try {
                        int number = Integer.parseInt(fieldValue);
                        if (number < 2010 || 2020 < number)  {
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    break;
                case "eyr":
                    try {
                        int number = Integer.parseInt(fieldValue);
                        if (number < 2020 || 2030 < number)  {
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    break;
                case "hgt":
                    boolean isCM = fieldValue.contains("cm");
                    int number;
                    try {
                        number = Integer.parseInt(fieldValue.replaceAll("cm", "").replaceAll("in", ""));
                    }catch (NumberFormatException e) {
                        return false;
                    }
                    if (isCM) {
                        if (number < 150 || 193 < number)  {
                            return false;
                        }
                    } else {
                        if (number < 59 || 76 < number)  {
                            return false;
                        }
                    }
                    break;
                case "hcl":
                    if (!fieldValue.matches("#[\\da-f]{6}")) {
                        return false;
                    }
                    break;
                case "ecl":
                    if (!validEyeColors.stream().anyMatch(e -> e.equalsIgnoreCase(fieldValue))) {
                        return false;
                    }
                    break;
                case "pid":
                    if (!fieldValue.matches("\\d{9}")) {
                        return false;
                    }
                    break;
                case "cid":
                    break;
            }

            return validFields.stream().anyMatch(f -> f.equalsIgnoreCase(fieldName));
        }
    }

}
