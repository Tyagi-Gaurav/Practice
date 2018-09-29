package gt.practice.kattis;

import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AlphabetSpam {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String line = sc.next();

        Map<String, Integer> collect = line.chars().mapToObj(x -> x)
                .collect(Collectors.groupingBy(AlphabetSpam::getIdentity,
                        Collectors.reducing(0, x -> 1, Integer::sum)));


        System.out.println(collect.getOrDefault("W", 0).doubleValue()/line.length());
        System.out.println(collect.getOrDefault("L", 0).doubleValue()/line.length());
        System.out.println(collect.getOrDefault("U", 0).doubleValue()/line.length());
        System.out.println(collect.getOrDefault("S", 0).doubleValue()/line.length());
    }

    private static String getIdentity(Integer a) {
        if (Character.isLowerCase(a)) {
            return "L";
        } else if (Character.isUpperCase(a)) {
            return "U";
        } else if (Character.valueOf((char)a.intValue()).equals('_')) {
            return "W";
        } else
            return "S";
    }
}
