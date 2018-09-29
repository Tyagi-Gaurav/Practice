package gt.practice.kattis;

import java.util.Scanner;
import java.util.stream.IntStream;

public class CryptoGraphersConundrum {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.next();
        int length = input.length();
        int numberOfPers = length / 3;

        String perString = IntStream.range(0, numberOfPers).mapToObj(x -> "PER")
                .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append).toString();

        int sum = IntStream.range(0, length)
                .mapToObj(x -> input.charAt(x) == perString.charAt(x) ? 0 : 1)
                .mapToInt(x -> x)
                .sum();

        System.out.println(sum);
    }
}
