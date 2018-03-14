package gt.practice.java8.misc;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectsRecipes {
    public static void main(String[] args) {
        int sum = Stream.of(1, null, 2, null, 3, 4, 5, null)
                .filter(Objects::nonNull)
                .mapToInt(Integer::valueOf)
                .sum();

        List<Double> collect = Stream.generate(Math::random)
                .limit(10)
                .collect(Collectors.toList());

        System.out.println(collect);
        System.out.println(sum);


        Random random = new Random();
        int sum1 = random.ints(5).sum();

        System.out.println(sum1);
    }
}
