package gt.practice.kattis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Skener {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int r = sc.nextInt();
        int c = sc.nextInt();
        int zr = sc.nextInt();
        int zc = sc.nextInt();

        List<String> output = new ArrayList<>();

        for (int i = 0; i < r; i++) {
            output.addAll(convert(sc.next(), zr, zc));
        }

        output.forEach(System.out::println);

    }

    private static List<String> convert(String next, int zr, int zc) {
        List<String> output = new ArrayList<>();

        for (int i = 0; i < zr; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < next.length(); j++) {
                for (int k = 0; k < zc; k++) {
                    builder.append(next.charAt(j));
                }
            }
            output.add(builder.toString());
        }

        return output;
    }
}
