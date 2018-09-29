package gt.practice.kattis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Zamka {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int l = sc.nextInt();
        int d = sc.nextInt();
        int x = sc.nextInt();

        List<Integer> results = new ArrayList<>();

        for (int i=l; i <= d; ++i) {
            if (x == sumOfDigits(i))
                results.add(i);
        }

        System.out.println(results.get(0));
        System.out.println(results.get(results.size() - 1));
    }

    public static int sumOfDigits(int x) {
        int sum = 0;

        while (x > 0) {
            sum += x % 10;
            x = x/10;
        }

        return sum;
    }
}
