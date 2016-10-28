package gt.practice.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class FilterUsingReduce {
    public static void main(String[] args) {
        FilterUsingReduce filterUsingReduce = new FilterUsingReduce();
        List<Integer> integers = asList(1, 2, 3, 4);
        List<Integer> collect = integers.stream().filter(x -> x > 2).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(filterUsingReduce.doIt(integers.stream(), x -> x > 2));
    }

    private <T> List<T> doIt(Stream<T> stream, Predicate<T> mapper) {
        return stream.reduce(new ArrayList<T>(),
                (List<T> acc, T x) -> {
                    List<T> arrayList = new ArrayList(acc);
                    if (mapper.test(x)) {
                        arrayList.add(x);
                    }

                    return arrayList;
                }, (List<T> left, List<T> right) -> {
                    System.out.println("Left: " + left);
                    System.out.println("Right: " + right);
                    List<T> newList = new ArrayList<T>(left);
                    newList.addAll(right);
                    return newList;
                });
    }
}
