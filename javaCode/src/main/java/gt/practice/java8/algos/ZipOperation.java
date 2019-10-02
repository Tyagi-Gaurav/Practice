package gt.practice.java8.algos;


import org.apache.commons.math3.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Given 2 lists of any length, we need to zip the 2 lists together.
 *
 * For example:
 *  A = [1, 2, 3]
 *  B = [4, 5, 6]
 *
 *  should produce C = [(1,4), (2, 5), (3, 6)]
 */
public class ZipOperation {
    public static void main(String[] args) {
        List<Integer> listA = Arrays.asList(1, 2, 3);
        List<Integer> listB = Arrays.asList(4, 5, 6);

        System.out.println(zip(listA, listB));

        listA = Arrays.asList(8, 2, 3, 1,9);

        System.out.println(zip(listA, listB));

    }

    private static java.util.List<Pair> zip(List<Integer> listA, List<Integer> listB) {
        return IntStream.range(0, Math.min(listA.size(), listB.size()))
                .mapToObj(x -> new Pair(listA.get(x), listB.get(x)))
                .collect(Collectors.toList());
    }


}
