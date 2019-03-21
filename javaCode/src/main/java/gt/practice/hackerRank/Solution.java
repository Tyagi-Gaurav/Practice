package gt.practice.hackerRank;

import java.io.IOException;
import java.util.Scanner;

public class Solution {

    // Complete the squares function below.
    static int squares(int a, int b) {
        int count = 0;

        double sqrt = Math.sqrt(a);
        int start = (int) sqrt;

        if (sqrt == start) {
            count++;
        }

        while (start * start + (start * 2 + 1) <= b) {
            count++;
            start++;
        }

        return count;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String[] ab = scanner.nextLine().split(" ");

            int a = Integer.parseInt(ab[0]);

            int b = Integer.parseInt(ab[1]);

            int result = squares(a, b);

            System.out.println(result);
            //bufferedWriter.write(String.valueOf(result));
            //bufferedWriter.newLine();
        }

        //bufferedWriter.close();

        scanner.close();
    }
}





