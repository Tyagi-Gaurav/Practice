package gt.practice.algos.arrays;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Anagrams {
    public static void main(String[] args) {
        Anagrams anagrams = new Anagrams();
        System.out.println(anagrams.find("abcdefg"));
    }

    private List<String> find(String str) {
        Stream<Character> characterStream = str.chars().mapToObj(i -> (char)i);
        ArrayList<String> collect = characterStream
                .collect(ArrayList<String>::new,
                        (BiConsumer<ArrayList<String>, Character>) (strings, character) -> {
                            List<String> interleave = interleave(strings, character);
                            strings.clear();
                            strings.addAll(interleave);
                        },
                        List::addAll);
        return collect;
    }


    private static List<String> interleave(String input, Character ch) {
        return IntStream.range(0, input.toCharArray().length+1)
                .mapToObj(x ->
                        input.substring(0, x) + ch +
                                input.substring(x)
                ).collect(Collectors.toList());
    }

    private static List<String> interleave(List<String> input, Character ch) {
        List<String> collect = null;

        if (input.size() == 0) {
            collect = new ArrayList<>();
            collect.add(String.valueOf(ch));
        } else {
            collect = input.stream()
                    .flatMap(x -> interleave(x, ch).stream())
                    .collect(Collectors.toList());
        }

        return collect;
    }
}
