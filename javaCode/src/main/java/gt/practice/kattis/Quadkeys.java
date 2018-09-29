package gt.practice.kattis;

import java.util.Scanner;

public class Quadkeys {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            String next = scanner.next();

            int len = next.length();
            int x = 0;
            int y = 0;

            char[] chars = next.toCharArray();
            for (char ch : chars) {
                x = x * 2 + getx(ch) ;
                y = y * 2 + gety(ch);
            }

            System.out.println(len + " " + x + " " + y);
        }
    }

    private static int gety(char ch) {
        switch(ch) {
            case '0' : return 0;
            case '1' : return 0;
            case '2' : return 1;
            case '3' : return 1;
        }

        return -1;
    }

    private static int getx(char ch) {
        switch(ch) {
            case '0' : return 0;
            case '1' : return 1;
            case '2' : return 0;
            case '3' : return 1;
        }

        return -1;
    }
}
