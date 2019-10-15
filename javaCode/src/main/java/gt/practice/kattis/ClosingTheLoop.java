package gt.practice.kattis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClosingTheLoop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfTests = scanner.nextInt();

        List<String> output = new ArrayList<>();

        for (int i = 0; i < numberOfTests; i++) {
            scanner.nextInt();
            scanner.nextLine();

            String ropeLengthInputs = scanner.nextLine();
            String[] ropeWithLengths = ropeLengthInputs.split(" ");

            output.add(String.format("Case #%d: %d", (i + 1), maxRopeLength(ropeWithLengths)));
        }

        output.forEach(System.out::println);
    }

    private static int maxRopeLength(String[] ropeWithLengths) {
        //Sort int R & B
        int[] rLengths = extract(ropeWithLengths, "R");
        int[] bLengths = extract(ropeWithLengths, "B");

        int sum = 0;

        //Scan each list from head and keep adding heads together until any/both lists finish.
        int r = rLengths.length;
        int b = bLengths.length;
        int count = 0;

        while (r - 1 >= 0 && b - 1 >= 0) {
            sum += rLengths[r - 1] + bLengths[b - 1];
            count += 2;

            r--;
            b--;
        }

        return sum - count;
    }

    private static int[] extract(String[] ropeWithLengths, String b) {
        int[] allLengths = Arrays.stream(ropeWithLengths).filter(x -> x.endsWith(b))
                .mapToInt(value -> Integer.parseInt(value.substring(0, value.lastIndexOf(b))))
                .toArray();

        if (allLengths.length > 0) {
            Arrays.sort(allLengths);
        }

        return allLengths;
    }
}
