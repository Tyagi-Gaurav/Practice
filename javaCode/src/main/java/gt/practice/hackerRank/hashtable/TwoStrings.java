package gt.practice.hackerRank.hashtable;

import java.util.AbstractCollection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TwoStrings {
    // Complete the twoStrings function below.
    static String twoStrings(String s1, String s2) {
        Set<Integer> firstChars = s1.chars()
                .collect(HashSet::new, HashSet::add, AbstractCollection::addAll);

        Set<Integer> secondChars = s2.chars()
                .collect(HashSet::new, HashSet::add, AbstractCollection::addAll);

        boolean result = firstChars.stream()
                .anyMatch(secondChars::contains);

        if (result)
            return "YES";
        else
            return "NO";
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String s1 = scanner.nextLine();

            String s2 = scanner.nextLine();

            System.out.println(twoStrings(s1, s2));
        }

        scanner.close();
    }
}
