package gt.practice.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class MapUsingReduce {
    public static void main(String[] args) {
        MapUsingReduce mapUsingReduce = new MapUsingReduce();
        List<Integer> integers = asList(1, 2, 3, 4);
        List<Integer> collect = integers.stream().map(x -> x * x).collect(Collectors.toList());
        System.out.println(collect);
        //System.out.println(mapUsingReduce.doIt(integers.stream(), x -> x * x));
        mapUsingReduce.doIt(integers.stream(), x -> x * x);
    }

    private <T, R> List<R> doIt(Stream<T> stream, Function<T, R> mapper) {
        return stream.reduce(new ArrayList<R>(),
                (List<R> acc, T x) -> {
                    List<R> arrayList = new ArrayList(acc);
                    arrayList.add(mapper.apply(x));
                    return arrayList;
                }, (List<R> left, List<R> right) -> {
                    System.out.println("Left: " + left);
                    System.out.println("Right: " + right);
                    List<R> newList = new ArrayList<R>(left);
                    newList.addAll(right);
                    return newList;
                });
    }
}
