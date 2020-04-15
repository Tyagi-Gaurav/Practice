package gt.practice.hackerRank.hashtable;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RansomNote {
    // Complete the checkMagazine function below.
    static void checkMagazine(String[] magazine, String[] note) {
        Map<String, Long> magazineWords = Arrays.stream(magazine)
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));

        if (canMakeWord(note, magazineWords))
            System.out.println("Yes");
        else
            System.out.println("No");

    }

    private static boolean canMakeWord(String[] note, Map<String, Long> magazineWords) {
        for (String word : note) {
            Long value = magazineWords.computeIfPresent(word, (s, aLong) -> {
                if (aLong > 0)
                    return aLong - 1;
                else
                    return null;
            });

            if (value == null) {
                return false;
            }
        }

        return true;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String[] mn = scanner.nextLine().split(" ");

        int m = Integer.parseInt(mn[0]);

        int n = Integer.parseInt(mn[1]);

        String[] magazine = new String[m];

        String[] magazineItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < m; i++) {
            String magazineItem = magazineItems[i];
            magazine[i] = magazineItem;
        }

        String[] note = new String[n];

        String[] noteItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            String noteItem = noteItems[i];
            note[i] = noteItem;
        }

        checkMagazine(magazine, note);

        scanner.close();
    }
}
