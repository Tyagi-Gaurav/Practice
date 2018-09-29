package gt.practice.kattis;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;

public class Kornislav {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        int d = sc.nextInt();

        List<Steps> combinations = findCombinations(new Steps(a, b, c, d));

        OptionalInt max = combinations.stream().filter(x -> x.a >= x.c && x.b <= x.d)
                .mapToInt(Kornislav::calculateArea)
                .max();

        System.out.println(max.orElse(0));
    }

    private static int calculateArea(Steps x) {
        int side1 = x.a > x.c ? x.c : x.a;
        int side2 = x.b < x.d ? x.b : x.d;
        return side1 * side2;
    }

    private static List<Steps> findCombinations(Steps steps) {
        List<Steps> combs = new ArrayList<>();

        int arr[] = new int[4];
        arr[0] = steps.a;
        arr[1] = steps.b;
        arr[2] = steps.c;
        arr[3] = steps.d;

        for (int i=0; i < 4; ++i) {
            for (int j=0;j < 4; ++j) {
                if (i != j) {
                    for (int k = 0; k < 4; ++k) {
                        if (k != i && k != j) {
                            for (int l = 0; l < 4; ++l) {
                                if (l != k && l != i && l != j) {
                                    combs.add(new Steps(arr[i], arr[j], arr[k], arr[l]));
                                }
                            }
                        }
                    }
                }
            }
        }


        return combs;
    }

    static class Steps {
        private int a;
        private int b;
        private int c;
        private int d;

        public Steps(int a, int b, int c, int d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }
}
