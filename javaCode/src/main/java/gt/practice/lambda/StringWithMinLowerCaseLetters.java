package gt.practice.lambda;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingLong;

public class StringWithMinLowerCaseLetters {
    public static void main(String[] args) {
        List<String> strings = asList("aBC", "",
                "DdghH",
                "HGHG",
                "dhfghdfgHH",
                "SHGHGHGJGJHGJ");

        Optional<String> min = strings.stream()
                .max(comparingLong(x -> x.chars().filter(Character::isLowerCase).count()));

        System.out.println(min.orElse("No String Found"));

    }
}
