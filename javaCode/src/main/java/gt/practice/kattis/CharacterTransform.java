package gt.practice.kattis;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CharacterTransform {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int tc = scanner.nextInt();

            List<Long> longs = playKey(1, 0, tc);

            System.out.println(longs.get(0) + " " + longs.get(1));
        }
    }

    private static List<Long> playKey(long acount, long bcount, int tc) {
        if (tc > 0) {
            return playKey(bcount, acount + bcount, tc - 1);
        }

        return Stream.of(acount, bcount).collect(Collectors.toList());
    }
}
