package gt.practice.kattis;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WhatDoesTheFoxSay {
    public static void main(String[] args) {
        List<List<String>> inputs = readFromConsole();

        inputs.stream()
                .map((List<String> x) -> {
                    String input = x.get(0);
                    Set<String> voices = x.stream().skip(1)
                            .map(y -> Arrays.asList(y.split("goes")).get(1).trim())
                            .collect(Collectors.toSet());
                    return Arrays.asList(input.split(" "))
                            .stream()
                            .filter(w -> !voices.contains(w))
                            .collect(Collectors.joining(" "));
                })
                .forEach(System.out::println);
    }

    private static List<List<String>> readFromConsole() {
        Scanner sc = new Scanner(System.in);
        int numberOfTests = sc.nextInt();

        return IntStream
            .rangeClosed(1, numberOfTests)
            .mapToObj(x -> {
                String line = sc.nextLine();
                List<String> strings = new ArrayList<>();
                while (!"what does the fox say?".equals(line)) {
                    if (!line.trim().equals(""))
                        strings.add(line);
                    line = sc.nextLine();
                }

                return strings;
            }).collect(Collectors.toList());
    }
}
