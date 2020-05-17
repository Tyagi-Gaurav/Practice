package gt.practice.hackerRank.problemsolving;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DayOfProgrammer {
    static String dayOfProgrammer(int year) {
        if (year > 1918) {
            if (isGregorianLeap(year)) {
                return String.format("12.09.%d", year);
            } else {
                return String.format("13.09.%d", year);
            }
        } else if (year < 1918) {
            if (isJulianLeap(year)) {
                return String.format("12.09.%d", year);
            } else {
                return String.format("13.09.%d", year);
            }
        }

        return String.format("26.09.%d", year);
    }

    private static boolean isJulianLeap(int year) {
        return year % 4 == 0;
    }

    private static boolean isGregorianLeap(int year) {
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int year = Integer.parseInt(bufferedReader.readLine().trim());

        String result = dayOfProgrammer(year);

        System.out.println(result);
        bufferedReader.close();
    }
}
