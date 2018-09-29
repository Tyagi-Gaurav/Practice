package gt.practice.kattis;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public class OddManOut {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<String> output = new ArrayList<>();

        int tc = sc.nextInt();
        for (int i = 0; i < tc; i++) {
            sc.nextInt();
            sc.nextLine();
            List<Long> guestNumbers = read(sc.nextLine());

            Map<Long, Long> reduce = guestNumbers.stream()
                    .collect(Collectors.groupingBy(identity(),
                            Collectors.reducing(0L, x -> 1L, Long::sum)));

            List<Long> collect = reduce.entrySet().stream().filter(x -> x.getValue() %2 != 0)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            output.add("Case #" + (i+1) + ": " + (collect.size() > 0 ? collect.get(0) : 0));
        }

        output.forEach(System.out::println);
    }

    private static List<Long> read(String s) {
        return Arrays.stream(s.split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
