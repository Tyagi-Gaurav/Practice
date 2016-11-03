package gt.practice.methodReferences;

import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class MapToUpperCase {
    public static void main(String[] args) {
        String originalString = "To be or not to be";
        String newString = asList(originalString.split(" "))
                .stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(" "));
        System.out.println(newString);
        System.out.println(newString.equals(originalString.toUpperCase()));
    }
}
