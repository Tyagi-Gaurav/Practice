package gt.practice.kattis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReverseBinary {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long input = sc.nextLong();
        List<Integer> x = numberToBinary(input);
        Integer reduce = x.stream().reduce(0, (s, y) -> 2 * s + y);
        System.out.println(reduce);
    }

    private static List<Integer> numberToBinary(long number) {
        List<Integer> binaryDigits = new ArrayList<>();
        while (number > 0) {
            binaryDigits.add((int) (number % 2));
            number = number / 2;
        }

        return binaryDigits;
    }
}
