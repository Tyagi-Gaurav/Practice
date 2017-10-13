package gt.practice.kattis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Spavanac {
    public static void main(String[] args) {
        List<Long> hourMinute = readFromConsole();
        Long hour = hourMinute.get(0);
        Long minute = hourMinute.get(1);

        subtractMinutesFrom(hour, minute, 45);
    }

    private static void subtractMinutesFrom(Long hour, Long minute, int duration) {
        if (minute > duration) {
            minute = minute - duration;
        } else {
            minute = (60 + (minute - 45)) % 60;

            if (hour > 0) {
                hour = hour - 1;
            } else {
                hour = 23L;
            }
        }
        System.out.println(hour + " " + minute);
    }

    private static List<Long> readFromConsole() {
        Scanner sc = new Scanner(System.in);
        long a = sc.nextLong();
        long b = sc.nextLong();

        List<Long> inputs = new ArrayList<>();
        inputs.add(a);
        inputs.add(b);

        return inputs;
    }
}
