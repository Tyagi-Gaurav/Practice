package gt.practice.hackerRank.arrays;

import java.io.IOException;
import java.util.Scanner;

public class CountingValleys {
    // Complete the countingValleys function below.
    static int countingValleys(int n, String s) {
        int v = 0;
        int total = 0;

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == 'U') {
                ++v;
                if (v == 0)
                    total++;
            } else if (s.charAt(i) == 'D')
                --v;
        }

        return total;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String s = scanner.nextLine();

        int result = countingValleys(n, s);

        System.out.println(result);

        scanner.close();
    }
}
