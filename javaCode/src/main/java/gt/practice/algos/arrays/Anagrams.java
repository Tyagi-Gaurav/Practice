package gt.practice.algos.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Anagrams {
    public static void main(String[] args) {
        Anagrams anagrams = new Anagrams();
        anagrams.zip(3);
    }

    private List<Pair<Integer>> zip(int x) {

        List<Integer> integers = Arrays.asList(1, 3, 2, 4, 5);
        int index = 0;
        ArrayList<Pair<Integer>> collect = integers
                .stream()
                .collect(ArrayList::new,
                        (list, num) -> new Pair<Integer>(num, list.size() + 1),
                        (list1, list2) -> list1.addAll(list2));


        System.out.println(collect);
        return collect;
    }

    private class Pair<T> {
        T x, y;

        public Pair(T x, T y) {
            this.x = x;
            this.y = y;
        }

        public T getX() {
            return x;
        }

        public T getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
