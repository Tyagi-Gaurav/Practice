package gt.codility;

import java.util.BitSet;

public class Solution {
    public int solution(int[] A, int k) {
        BitSet possible = new BitSet(100000);

        k = 3;
        for (int i = 0; i < A.length; i++) {
            if (A[i] >= 1)
                possible.set(A[i] - 1);
        }

        int index = possible.nextClearBit(0);

        return index == -1 ? 1 : index+1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int A[] = {1, 3, 6, 4, 1, 2};
        System.out.println(solution.solution(A, 3));

        String color = "red";

        switch (color) {
            case "red" :
                System.out.println("red");
            case "green" :
                System.out.println("green");
        }
    }
}
