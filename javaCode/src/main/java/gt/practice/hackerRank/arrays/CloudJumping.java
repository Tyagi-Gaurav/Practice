package gt.practice.hackerRank.arrays;

import java.io.IOException;
import java.util.Scanner;

public class CloudJumping {
    // Complete the jumpingOnClouds function below.
    static int jumpingOnClouds(int[] c) {
        int steps = 0;

        int i = 0;
        int length = c.length;
        while (i < length - 1) {
            if (i + 2 < length && c[i + 2] == 0) {
                i += 2;
            } else if (i + 1 < length && c[i + 1] == 0) {
                i += 1;
            } else {
                break;
            }

            steps++;
        }

        return steps;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] c = new int[] {0, 0, 0, 0, 1, 0};

        //String[] cItems = scanner.nextLine().split(" ");
        //scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

//        for (int i = 0; i < n; i++) {
//            int cItem = Integer.parseInt(cItems[i]);
//            c[i] = cItem;
//        }

        int result = jumpingOnClouds(c);

        System.out.println(result);
        scanner.close();
    }
}

