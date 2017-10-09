package gt.practice.algos.arrays;


import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class TakeWhile<T> {
    public static void main(String[] args) {
        TakeWhile<Integer> takeWhile = new TakeWhile();
        takeWhile.takeWhile(Arrays.asList(1, 2, 3, 4), x -> x % 2 == 0);
    }

    private List<T> takeWhile(List<T> input, Predicate<T> p) {
        return null;
    }

}