package gt.practice.algos.arrays;

import java.util.Arrays;

public class MinMaxSum {
    // Complete the miniMaxSum function below.
    static void miniMaxSum(int[] arr) {
        long sum = Arrays.stream(arr).mapToLong(Long::valueOf).sum();

        long min = sum;
        long max = 0;

        for (int i = 0; i < arr.length; i++) {
            long temp = sum - arr[i];
            if (max < temp) {
                max = temp;
            }

            if (min > temp && temp > 0) {
                min = temp;
            }
        }

        System.out.println(min + " " + max);
    }

    public static void main(String[] args) {
        //miniMaxSum(new int[] {1, 2, 3, 4, 5});
        miniMaxSum(new int[] {256741038, 623958417, 467905213, 714532089, 938071625});
    }
}
