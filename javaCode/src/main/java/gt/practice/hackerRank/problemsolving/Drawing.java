package gt.practice.hackerRank.problemsolving;

import java.util.Scanner;

public class Drawing {
    /*
     * Complete the pageCount function below.
     * Beginning:
     * 1 - 0
     * 2, 3 - 1 -> 2/2 = 1, 3/2 = 1
     * 4, 5 - 2 -> 4/2 = 2, 5/2 = 2
     * 6, 7 - 3 -> 6/2 = 3, 7/2 = 3
     *
     *
     * End:
     * n is even: 0
     * n-1, n-2: 1 -> (n - (n-1) + 1)/2, (n - (n-2) + 1)/2
     * n-3, n-4: 2 -> (n - (n-3) + 1)/2, (n - (n-4) + 1)/2
     *
     * n is odd:
     * n, n - 1: 0 -> (n - n)/2, (n - (n-1))/2
     * n -2, n - 3: 1 -> (n - (n-2))/2, (n - (n-3))/2
     * N: Odd
     */
    static int pageCount(int n, int p) {
        int numberOfFlipsFromBeginning = p/2;
        int numberOfFlipsFromEnd = 0;

        if (n % 2 == 0) {
            numberOfFlipsFromEnd = (n - p + 1)/2;
        } else {
            numberOfFlipsFromEnd = (n - p)/2;

        }

        return Math.min(numberOfFlipsFromBeginning, numberOfFlipsFromEnd);
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int n = scanner.nextInt();
        //scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

        int p = scanner.nextInt();
        //scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

        int result = pageCount(n, p);

        System.out.println(result);

        scanner.close();
    }
}
