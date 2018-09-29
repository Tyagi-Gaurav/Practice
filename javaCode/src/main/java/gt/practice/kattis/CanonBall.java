package gt.practice.kattis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CanonBall {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();

        List<String> output = new ArrayList<>();

        for (int i=0;i < tc;++i) {
            double velocity = sc.nextDouble();
            double angle = sc.nextDouble();
            double distance = sc.nextDouble();
            double height1 = sc.nextDouble();
            double height2 = sc.nextDouble();

            double t = distance/(velocity * Math.cos(Math.toRadians(angle)));

            double yresult = getYresult(velocity, angle, t);

            if (yresult >= height1 + 1 && yresult <= height2 - 1) {
                output.add("Safe");
            } else {
                output.add("Not Safe");
            }
        }

        output.forEach(System.out::println);
    }

    private static double getYresult(double velocity, double angle, double t) {
        return velocity * t * Math.sin(Math.toRadians(angle)) - 0.5 * 9.8 * t * t;
    }
}
