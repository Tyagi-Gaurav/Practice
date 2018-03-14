package gt.practice.java8.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class FileProc {
    public static void main(String[] args) {
        findLongestWords(10);
    }

    private static void findLongestWords(int i)  {
        try (Stream<String> lines = Files.lines(Paths.get("/usr/share/dict/words"))) {
            lines.filter(x -> x.length() > 20)
                    .sorted(Comparator.comparingInt(String::length).reversed())
                    .limit(i)
                    .forEach(w -> System.out.printf("%s (%d)%n", w, w.length()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
