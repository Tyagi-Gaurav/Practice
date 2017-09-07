package gt.practice.algos.arrays;

import java.util.Scanner;

public class MaxContiguousSum {

    public static int [] readNumsFromCommandLine() {

        Scanner s = new Scanner(System.in);

        int count = s.nextInt();
        s.nextLine(); // throw away the newline.

        int [] numbers = new int[count];
        try (Scanner numScanner = new Scanner(s.nextLine())) {
            for (int i = 0; i < count; i++) {
                if (numScanner.hasNextInt()) {
                    numbers[i] = numScanner.nextInt();
                } else {
                    System.out.println("You didn't provide enough numbers");
                    break;
                }
            }
        }

        return numbers;
    }


    public static void main(String[] args) {
//        int a[] = {-2, -3, 4, -1, -2, 1, 5, -3};
        int a[] = readNumsFromCommandLine();

        int maxSoFar = a[0];
        int currentMax = a[0];

        for (int i=1; i < a.length;++i) {
            if (needToResetCurrentMax(a[i], currentMax + a[i])) {
                currentMax = a[i];
            } else {
                currentMax += a[i];
            }

            if (currentMax > maxSoFar) {
                maxSoFar = currentMax;
            }
        }

        System.out.println(maxSoFar);
    }

    private static boolean needToResetCurrentMax(int i, int newMax) {
        return newMax <= 0 || i > newMax;
    }
}
