package gt.practice.kattis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Misc {
    public static void main(String[] args) {
        List<Long> longs = readFromConsole();

        Long reduce = longs.stream()
                .map(x -> (long) Math.pow(x / 10, x % 10))
                .reduce(0L, (s, x) -> s + x);

        System.out.println(reduce);
    }

    private static List<Long> readFromConsole() {
        Scanner sc = new Scanner(System.in);
        long N = sc.nextLong();
        List<Long> inputs = new ArrayList<>();

        for (int i =0; i < N;++i)
            inputs.add(sc.nextLong());

        return inputs;
    }
}
