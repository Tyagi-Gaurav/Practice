package gt.practice.java8.sort;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortingMaps {
    public static void main(String[] args) throws IOException {
        readDictionaryIntoMap();
    }

    private static void readDictionaryIntoMap() throws IOException {
        Path path = FileSystems.getDefault().getPath("/usr/share/dict/words");
        try (Stream<String> lines = Files.lines(path)) {
            Map<Integer, Long> collect = lines.filter(x -> x.length() > 20)
                    .collect(Collectors.groupingBy(
                            String::length, Collectors.counting()));

            collect.forEach((key, value) -> System.out.println("key -> " + key + ", value -> " + value));

            collect.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                    .forEach(e -> System.out.printf("Length: %d: %2d words %n", e.getKey(), e.getValue()));
        }
    }
}
