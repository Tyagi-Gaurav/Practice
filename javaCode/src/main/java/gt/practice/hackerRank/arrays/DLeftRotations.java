package gt.practice.hackerRank.arrays;

import java.util.Scanner;

public class DLeftRotations {
    // Complete the rotLeft function below.
    static int[] rotLeft(int[] a, int d) {
        reverse(a, 0, a.length - 1);
        reverse(a, a.length - d, a.length - 1);
        reverse(a, 0, a.length - d - 1);
        return a;
    }

    private static void reverse(int[] a, int s, int e) {
        while (s <= e) {
            int temp = a[s];
            a[s] = a[e];
            a[e] = temp;
            s++; e--;
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String[] nd = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nd[0]);

        int d = Integer.parseInt(nd[1]);

        int[] a = new int[n];

        String[] aItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int aItem = Integer.parseInt(aItems[i]);
            a[i] = aItem;
        }

        int[] result = rotLeft(a, d);

        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]);

            if (i != result.length - 1) {
                System.out.println(" ");
            }
        }

        scanner.close();
    }
}
