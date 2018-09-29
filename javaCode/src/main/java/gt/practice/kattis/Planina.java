package gt.practice.kattis;

import java.util.Scanner;

public class Planina {
    public static void main(String[] args) {
        Integer number = readFromConsole();

        Integer result = calculate(number);
        System.out.println(result * result);
    }

    private static Integer calculate(Integer number) {
        if (number == 0) return 2;
        else return (2 * calculate(number - 1) - 1);
    }

    private static Integer readFromConsole() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
}
