import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {
        File inputFile = new File("input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int emptyLines = 0;
            int numberOfPeople = 0;
            int count = 0;
            String line;
            Group currentGroup = new Group();
            List<Group> groups = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                count++;
//                System.out.println("" + count + " " + line);
                if (line.isEmpty()) {
//                    System.out.println("adding group");
                    emptyLines++;
                    groups.add(currentGroup);
                    currentGroup = new Group();
                } else {
//                    System.out.println("adding person");
                    currentGroup.addPerson(line);
                    numberOfPeople++;
                }
            }

            System.out.println("adding final group");
            emptyLines++;
            groups.add(currentGroup);
            currentGroup = new Group();


            System.out.println("groups " + groups);
            int sum = emptyLines + numberOfPeople;
            System.out.println("Number of groups: " + groups.size());
            int numberOfUniqueAnswers = 0;
            int numberOfDuplicateAnswers = 0;
            for(Group group: groups) {
                numberOfUniqueAnswers += group.getUniqueAnswers().length();
                numberOfDuplicateAnswers += group.getDuplicateAnswers().length();
            }
            System.out.println("Sum of unique counts: " + numberOfUniqueAnswers);
            System.out.println("Sum of duplicate counts: " + numberOfDuplicateAnswers);
            System.out.println("p: " + numberOfPeople + " e: " + emptyLines + " s: " + sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class Group {

        private List<String> people;

        Group() {
            people = new ArrayList<>();
        }

        public void addPerson(String person) {
            people.add(person);
        }

        public String getUniqueAnswers() {
            String bigString = "";
            for (String person: people) {
                bigString += person;
            }

            char[] chars = bigString.toCharArray();
            Set<Character> characterSet = new HashSet<>();
            for (char x: chars) {
                characterSet.add(x);
            }
            String uniqueAnswers = "";
            for (Character character: characterSet) {
                uniqueAnswers += character;
            }
            return uniqueAnswers;
        }

        public String getDuplicateAnswers() {
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            int[] counts = new int[26];
            for(String person: people) {
                for (char c: person.toCharArray()) {
                    counts[c - 'a']++;
                }
            }
            String duplicateAnswers = "";
            for (char x: alphabet.toCharArray()) {
                if (counts[x - 'a'] == people.size()) {
                    duplicateAnswers += x;
                }
            }
            return duplicateAnswers;
        }
    }
}
