package gt.practice.kattis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Beavergnaw {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Double> output = new ArrayList<>();

        while (true) {
            int D = sc.nextInt();
            int V = sc.nextInt();

            if (D == 0 && V == 0) break;

            output.add(Math.cbrt((Math.PI * D * D * D - 6 * V)/ Math.PI));
        }

        output.forEach(System.out::println);

        sc.close();
    }
}
