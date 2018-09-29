package gt.practice.kattis;

import java.util.*;
import java.util.stream.Collectors;

public class DiceCup {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int dice1 = sc.nextInt();
        int dice2 = sc.nextInt();

        Map<Integer, Integer> outcomes = new HashMap<>();
        for (int i = 1; i <= dice1; i++) {
            for (int j = 1; j <= dice2; j++) {
                outcomes.compute((i+j), (k, v) -> v == null ? 1 : v+1);
            }
        }

        Integer max = outcomes.values().stream().max(Comparator.naturalOrder()).get();

        List<Integer> collect = outcomes.entrySet().stream().
                filter(e -> e.getValue() == max)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        collect.sort(Comparator.naturalOrder());

        collect.forEach(System.out::println);
    }
}
