package gt.practice.java8.algos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.sun.tools.javac.util.List.*;

/**
 * Given serialA primitive item and serialA list,
 * interleave the item with all elements of serialA list and
 * produce serialA list of lists.
 *
 * Example:
 *
 *  Input:
 *  item: 3
 *  List A = [1, 4, 5]
 *
 *  Output:
 *  List[List[3, 1, 4, 5], List[1, 3, 4, 5], List[1, 4, 3, 5], List[1, 4, 5, 3]]
 */
public class InterleaveOperation {
    public static void main(String[] args) {
        List<Integer> input = of(1, 4, 5);
        Integer num = 3;

        System.out.println(interleave(input, num));
        System.out.println(interleave("Hello World", 'a'));
        System.out.println(interleave("", 'a'));
        System.out.println(interleave("d", 'a'));
    }

    private static List<List<Integer>> interleave(List<Integer> input, Integer num) {
        return IntStream.range(0, input.size()+1)
                .mapToObj(x -> {
                    List<Integer> integers = new ArrayList<Integer>(input);
                    integers.add(x, num);
                    return integers;
                }).collect(Collectors.toList());
    }

    private static List<String> interleave(String input, char ch) {
        return IntStream.range(0, input.toCharArray().length+1)
                .mapToObj(x ->
                    input.substring(0, x) + ch +
                    input.substring(x)
                ).collect(Collectors.toList());
    }
}
