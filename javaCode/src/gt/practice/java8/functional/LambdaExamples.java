package gt.practice.java8.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by gauravt on 22/09/15.
 */
public class LambdaExamples {
    private static List<String> wordList = null;

    public static void main(String[] args) {
        convertWordsToUpperCase();
        findEvenLengthWordsInTheList();
        joinLinesTogetherIntoASingleString();
        findLengthOfLongestLine();
        returnListOfLowerCaseWordsInAlphabeticalOrder();
    }

    private static void returnListOfLowerCaseWordsInAlphabeticalOrder() {
        initialise();
        printDelimiters();
        List<String> collect = wordList.stream().map(String::toLowerCase).sorted().collect(Collectors.toList());
        System.out.println("Sorted List : " + collect);
        printDelimiters();
    }

    private static void findLengthOfLongestLine() {
        initialise();
        printDelimiters();
        int longestLength = wordList.stream().mapToInt(String::length).max().getAsInt();
        System.out.println("The longest word length is " + longestLength);
        printDelimiters();
    }

    private static void joinLinesTogetherIntoASingleString() {
        initialise();
        printDelimiters();
        String collect = wordList.stream().skip(1).limit(2).collect(Collectors.joining());
        System.out.println("After joining : " + collect);
        printDelimiters();
    }

    private static void findEvenLengthWordsInTheList() {
        initialise();
        printDelimiters();
        long count = wordList.stream().filter(e -> e.length() % 2 == 0).count();
        System.out.println("Number of Even Length Words " + count);
        printDelimiters();
    }

    private static void convertWordsToUpperCase() {
        initialise();
        printDelimiters();
        System.out.println("Converting Words to Uppercase");
        List<String> collect = wordList.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println("After Collection: " + collect);
        printDelimiters();
    }

    private static void printDelimiters() {
        System.out.println("******************************");
    }

    private static void initialise() {
        wordList = new ArrayList<>();
        wordList.add("Hello");
        wordList.add("abc");
        wordList.add("def");
        wordList.add("xyz");
        wordList.add("ahdg");
    }
}
