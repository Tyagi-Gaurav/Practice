package gt.practice.hackerRank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution_Binarysearch_Climbing_LeaderBoard {

    // Complete the climbingLeaderboard function below.
    static int[] climbingLeaderboard(int[] scores, int[] alice) {
        int[] result = new int[alice.length];
        List<Integer> newArray = new ArrayList<>();
        newArray.add(scores[0]);
        int j = 0;

        for (int i = 1; i < scores.length; i++) {
            if (newArray.get(j) != scores[i]) {
                newArray.add(scores[i]);
                ++j;
            }
        }

        Integer[] output = newArray.toArray(new Integer[0]);

        for (int i = 0; i < alice.length; i++) {
            result[i] = binarySearch(output, 0, output.length-1, alice[i]);
        }

        return result;
    }

    /*
    For key to be in the right place, when
    1. output[a] > key > output[b], then rank of key = b-1
    2. output[a] == key > output[b], then rank of key = a
    3. output[a] > key == output[b], then rank of key = b
    4. key > output[a] > output[b], then rank of key = a-1
     */
    private static int binarySearch(Integer[] output, int a, int b, int key) {
        //a is always <= b
        int mid = (a + b) / 2;

        if (a == b || b - a == 1) {
            //a & b cannot be 0.
            if (output[a] > key && key > output[b]) return a + 2;
            if (output[a] == key && key > output[b]) return a + 1;
            if (output[a] > key && key == output[b]) return b + 1;
            if (key > output[a] && output[a] > output[b]) {
                if (a == 0) return 1;
                else return a;
            }
            if (output[a] > key && output[b] > key) return b+2;
        }

        /*
        if key > output[mid] b = mid
        if output[mid] > key a = mid
        if key = output[mid] return mid+1;
        */

        if (key > output[mid]) {
            return binarySearch(output, a, mid, key);
        } else if (output[mid] > key) {
            return binarySearch(output, mid, b, key);
        } else {
            return mid + 1;
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int scoresCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] scores = new int[scoresCount];

        String[] scoresItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < scoresCount; i++) {
            int scoresItem = Integer.parseInt(scoresItems[i]);
            scores[i] = scoresItem;
        }

        int aliceCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] alice = new int[aliceCount];

        String[] aliceItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < aliceCount; i++) {
            int aliceItem = Integer.parseInt(aliceItems[i]);
            alice[i] = aliceItem;
        }

        int[] result = climbingLeaderboard(scores, alice);

        Arrays.stream(result).forEach(System.out::println);
//        for (int i = 0; i < result.length; i++) {
//            bufferedWriter.write(String.valueOf(result[i]));
//
//            if (i != result.length - 1) {
//                bufferedWriter.write("\n");
//            }
//        }

//        bufferedWriter.newLine();
//
//        bufferedWriter.close();

        scanner.close();
    }
}