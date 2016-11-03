package gt.practice.collectors;

import static java.util.Arrays.asList;

public class AggregatingStringWithCustomCollector {
    public static void main(String[] args) {
        String obj = asList("To be or not to be".split(" "))
                .stream()
                .collect(new CustomStringCollector("[", "]", ","));
        System.out.println(obj);
    }
}
