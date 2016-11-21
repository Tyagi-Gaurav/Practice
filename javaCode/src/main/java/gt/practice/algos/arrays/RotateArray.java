package gt.practice.algos.arrays;

import java.util.Arrays;
import java.util.List;

public class RotateArray {
    public static void main(String[] args) {
        Integer a[] = {1, 2, 3, 4, 5, 6, 7};

        rotateArray(a, 3);
        rotateArray(a, 7);
        rotateArray(a, 10);
        rotateArray(a, 11);
    }

    private static void rotateArray(Integer[] a, int times) {
        Integer[] p1 = Arrays.copyOf(a, a.length);
        System.out.println("\nRotating " + times + " times: ");
        rotateByReverse(p1, times);
        Arrays.asList(p1)
            .stream()
            .forEach(System.out::print);
    }

    private static void rotateByReverse(Integer[] a, int rotationTimes) {
        rotationTimes = rotationTimes % a.length;

        if (rotationTimes > 0) {
            int index = a.length - rotationTimes - 1;
            reverse(a, 0, index);
            reverse(a, index + 1, a.length - 1);
            reverse(a, 0, a.length - 1);
        }
    }

    private static void reverse(Integer[] a, int start, int end) {
        for (int i=0; i <= (end - start)/2; ++i) {
            int temp = a[i+start];
            a[i+start] = a[end -i];
            a[end-i] = temp;
        }
    }
}
