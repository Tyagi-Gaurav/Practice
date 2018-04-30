package gt.practice.kattis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AddBigIntegers {
    public static void main(String[] args) {
        List<String> numbers = readFromConsole();

        String numA = numbers.get(0);
        String numB = numbers.get(1);

        char[] charsA = numA.toCharArray();
        char[] charsB = numB.toCharArray();

        int indexA = numA.length();
        int indexB = numB.length();

        ArrayList<Integer> characters = new ArrayList<>();
        int max = Math.max(indexA, indexB);
        int carry = 0;
        for (int i = 0; i < max; ++i) {
            int digitA = getDigit(--indexA, charsA);
            int digitB = getDigit(--indexB, charsB);

            int sum = (digitA + digitB + carry);
            int digitC = sum % 10;
            carry = sum/10;
            characters.add(digitC);
        }

        if (carry > 0)
            characters.add(carry);

        Collections.reverse(characters);
        characters.forEach(System.out::print);
    }


    private static int getDigit(int index, char[] charsA) {
        if (index >= charsA.length || index < 0) return 0;
        else return charsA[index] - 48;
    }

    private static List<String> readFromConsole() {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> strings = new ArrayList<>();
        strings.add(sc.next());
        strings.add(sc.next());
        return strings;
    }
}
