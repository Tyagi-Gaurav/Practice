package gt.practice.java8.streams;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamsPrac {
    public static void main(String[] args) {
        infiniteStream();
        generateXRandomNumbers(10);
        sumAllIntegersFrom(10,35);
        boxedStreams();
        createArrayfromStreams();
    }

    private static void createArrayfromStreams() {
        int[] ints = IntStream.of(1, 2, 3, 4).toArray();
        System.out.println(ints);
    }

    private static void boxedStreams() {
        List<Integer> collect = IntStream.of(1, 2, 3)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> collect1 = IntStream.of(1, 2, 3)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());
        List<Integer> collect2 = IntStream.of(1, 2, 3)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println(collect);
        System.out.println(collect1);
        System.out.println(collect2);
    }

    private static void sumAllIntegersFrom(int low, int high) {
        System.out.println(IntStream.range(low, high).sum());
    }

    private static void generateXRandomNumbers(int count) {
        List<Double> collect = Stream.generate(Math::random).limit(count).collect(Collectors.toList());
        System.out.println(collect);
    }

    private static void infiniteStream() {
        /*
        This is useful when you have a way to
        produce the next value of the stream
        from the current value
         */
        List<BigDecimal> iterate = Stream.iterate(BigDecimal.ZERO, n -> n.add(BigDecimal.ONE))
                .limit(10)
                .collect(toList());
        System.out.println(iterate);
    }
}
