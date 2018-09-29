package gt.practice.kattis;

import java.util.Scanner;

public class Misc {

    public static void main(String[] args) throws InterruptedException {
        try(Scanner scanner = new Scanner(System.in)) {
            int p = scanner.nextInt();
            int t = scanner.nextInt();

            int result = 0;

            for (int i = 0; i < p ; i++) {
                int count = 0;
                for (int j = 0; j < t; j++) {
                    String tc = scanner.next();

                    if (isLower(tc)) count ++;
                }

                if (count == t) {
                    result ++;
                }
            }

            System.out.println(result);
        }
    }

    private static boolean isLower(String tc) {
        long count1 = tc.chars()
                .filter(x -> x >= 65 && x <= 90)
                .count();

        long count2 = tc.chars().skip(1).filter(x -> x >= 65 && x <= 90)
                .count();

        return count1 == 0 || count2 == 0;
    }
}
