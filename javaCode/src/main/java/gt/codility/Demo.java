package gt.codility;

import java.util.BitSet;

public class Demo {
    public static void main(String[] args) {
        System.out.println(solution(new int[]{1, 2, 3}));
        System.out.println(solution(new int[]{-1, -3}));
        System.out.println(solution(new int[]{1, 3, 6, 4, 1, 2}));
        System.out.println(solution(new int[]{-1, -3, 0}));
    }

    public static int solution(int[] A) {
        BitSet bitSet = new BitSet(1_000_000+1);

        for(int i = 0; i < A.length; ++i) {
            if (A[i] > 0)
                bitSet.set(A[i]);
        }

        return bitSet.nextClearBit(1);
    }
}
