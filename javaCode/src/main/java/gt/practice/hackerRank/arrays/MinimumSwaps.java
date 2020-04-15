package gt.practice.hackerRank.arrays;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MinimumSwaps {
    // Complete the minimumSwaps function below.
        /*
            1. For each element, determine where it should go.
            2. Add it to the graph for that element or the element that needs to come there.
            3. For each graph, calculate number of elements
            4. Total number of swaps is number of elements in the cycle - 1.

            How to model the graph (Uni-directed)?
            a) Have separate sets
            b) For each index, inspect number on index
                c) If (number != index)
                    d) Add number to set
                    e) Move to next number
            c) For each set, number of swaps is the number of elements in the cycle - 1.
         */

    static int minimumSwaps(int[] arr) {
        List<Set<Integer>> cycles = new ArrayList<>();
        BitSet bitSet = new BitSet(arr.length + 1);
        bitSet.set(0);

        for (int i = 0; i < arr.length; i++) {
            int element = arr[i];
            if (!bitSet.get(element)) {
                if (i == arr[i] - 1) {
                    //Element in its position
                } else {
                    HashSet<Integer> currentCycle = new HashSet<>();
                    currentCycle.add(element);
                    int nextElement = -1, target = element;
                    do {
                        nextElement = arr[element - 1];
                        currentCycle.add(nextElement);
                        element = nextElement;
                        bitSet.set(nextElement);
                    } while (element != target);

                    cycles.add(currentCycle);
                }
                bitSet.set(element);
            }
        }

        return cycles.stream()
                .mapToInt(s -> s.size() - 1)
                .sum();
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] arr = new int[n];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        int res = minimumSwaps(arr);

        System.out.println(res);

        scanner.close();
    }
}
