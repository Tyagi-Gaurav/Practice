package gt.practice.hackerRank.arrays;

import java.util.BitSet;
import java.util.Scanner;

public class MinimumBribes {
    /**
     *
     * What information do we need to make a decision?
     * How many operations did a number do before it could come forward?
     * Or, how many numbers did we not see before we see a number.
     *
     * Example: 1 2 5 3 7 8 6 4
     *
     * For 1 & 2, they are at their normal position.
     * For 5, we have not seen 3 & 4, so it must have bribed them. Total bribes: 2
     * For 3, we have seen 1 & 2, so no bribes.
     * For 7, we have not seen 6 & 4, so it must have bribed them. Total bribes: 2
     * For 8, we have not seen 6 & 4, so it must have bribed them. Total bribes: 2
     * For 6, we have not seen 4, so it must have bribed them. Total bribes: 1
     * For 4, we have seen all numbers before it, so no bribes given.
     *
     *
     * Example: 5 1 2 3 7 8 6 4
     * For 5, we have not seen 1, 2, 3 & 4. And the total count of numbers not seen, exceed 2. Hence this is
     * too chaotic.
     *
     * Solution: We use BitArray to keep track of numbers not seen so far. When we see a number, we find count
     * of bits that are still set before we saw the number. The cardinality of those bits is the number
     * of operations done. Keep adding that to get the answer.
     *
     * @param q
     */
    static void minimumBribes(int[] q) {
        int mb = 0;
        BitSet bitSet = new BitSet(q.length+1);
        bitSet.flip(0, q.length+1);
        bitSet.clear(0);
        boolean tooChaotic = false;

        for (int i = 0; i < q.length; i++) {
            BitSet tempBitSet = bitSet.get(0, q[i]);

            int cardinality = tempBitSet.cardinality();
            if (cardinality > 2) {
                tooChaotic = true;
            } else {
                mb += cardinality;
            }
            bitSet.clear(q[i]);
        }

        if (tooChaotic)
            System.out.println("Too chaotic");
        else
            System.out.println(mb);
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] q = new int[n];

            String[] qItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int qItem = Integer.parseInt(qItems[i]);
                q[i] = qItem;
            }

            minimumBribes(q);
        }

        scanner.close();
    }
}
