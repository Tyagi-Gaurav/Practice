package gt.practice.lambda;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;

public class TestLambda {
    public static void main(String[] args) {
        BinaryOperator<Integer> add = (x,y) -> x + y;
        System.out.println(add.apply(2,3));

        ThreadLocal<DateFormatter> dateFormatterThreadLocal =
                ThreadLocal.withInitial(() -> new DateFormatter(new SimpleDateFormat("dd-MM-yyyy")));
        System.out.println(dateFormatterThreadLocal.get().getFormat().format(new Date()));

        Runnable helloWorld = () -> System.out.println("Hello World");
        Executors.newCachedThreadPool().submit(helloWorld);

        List<Integer> collect = Stream.of(1, 2, 3).collect(Collectors.toList());
        System.out.println(collect);

        List<String> collect1 = Stream.of("serialA", "parallelB", "hello").map(x -> x.toUpperCase()).collect(Collectors.toList());
        System.out.println(collect1);

        List<Integer> collect2 = Stream.of(asList(1, 2, 3), asList(4, 5, 6))
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());
        System.out.println(collect2);

        Optional<Integer> min = Stream.of(10, 9, 8, 23, -4, 5, 1, 3, 4, 6, 28).min(comparing(x -> x));
        Supplier<Integer> integerSupplier = () -> 0;
        System.out.println(min.orElseGet(integerSupplier));
    }
}
