package gt.practice.hackerRank.arrays;

import java.util.Scanner;

public class HourGlass {
    // Complete the hourglassSum function below.
    static int hourglassSum(int[][] arr) {
        int max_sum = Integer.MIN_VALUE;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (i + 2 < 6 && j + 2 < 6) {
                    int k = getHourGlassSum(arr, i, j);
                    if (k > max_sum)
                        max_sum = k;
                }
            }
        }
        return max_sum;
    }

    private static int getHourGlassSum(int[][] arr, int r, int c) {
        int total = 0;

        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                total += arr[i][j];
            }
        }

        total -= (arr[r + 1][c] + arr[r + 1][c + 2]);

        return total;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int[][] arr = new int[6][6];

        for (int i = 0; i < 6; i++) {
            String[] arrRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 6; j++) {
                int arrItem = Integer.parseInt(arrRowItems[j]);
                arr[i][j] = arrItem;
            }
        }

        int result = hourglassSum(arr);
        System.out.println(result);

        scanner.close();
    }
}
