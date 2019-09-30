package gt.practice.kattis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CodeJam {
    private static final String SUB_STRING = "welcome to code jam";

    public String solve(String input) {
        /**
         * - Create a map of all characters in input to their positions available.
         * - If all characters not found then return 0
         * - For each character in substring:
         *      - get the set for previous character
         *      - get the set for the current element
         *          - For each newElement in the new set
         *              previousSet.anyMatch()
         *
         *              if (newElement < all elements of previous set) drop newElement from new set.
         *              if (any element of previous set < newElement) then
         *                  remove all y from previous set such that y > newElement.
         *
         */
        Map<Integer, Set<Integer>> mapOfallCharacters = createMapOfAllCharacters(input);
        if (mapOfallCharacters.size() != 11) {
            return formatResult(0);
        }

        //print(mapOfallCharacters);

        List<Item> itemList = new ArrayList<>();
        mapOfallCharacters.get((int)SUB_STRING.charAt(0)).forEach(
                index ->
                    itemList.add(new Item(0, index))
                );

        long total = 0;
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            int nextIndex = item.indexInSubstring+1;

            char key = SUB_STRING.charAt(nextIndex);
            List<Integer> eligibleIndexes =
                    getEligibleIndexes(mapOfallCharacters.get((int)key),
                            item.indexInMainString);
            if (nextIndex == SUB_STRING.length() - 1) {
                total += eligibleIndexes.size();
            } else {
                eligibleIndexes.forEach(x ->
                        itemList.add(new Item(nextIndex, x)));
            }
        }

        return formatResult(total);
    }

    private List<Integer> getEligibleIndexes(Set<Integer> integers, int indexInMainString) {
        return integers.stream().filter(x -> x > indexInMainString).collect(Collectors.toList());
    }

    private void print(Map<Integer, Set<Integer>> mapOfallCharacters) {
        mapOfallCharacters.keySet()
                .forEach(key -> {
                    System.out.println("Key: " + (char) key.intValue() + " value: " + mapOfallCharacters.get(key));
                });

    }

    private String formatResult(long i) {
//        System.out.println(i);
        int count = 0;
        long sum = 0;
        int multiplier = 1;
        while (count < 4 && i > 0) {
            sum = sum + i % 10 * multiplier;
            ++count;
            i = i / 10;
            multiplier *= 10;
        }

        return String.format("%04d", sum);
    }

    private Map<Integer, Set<Integer>> createMapOfAllCharacters(String input) {
        return SUB_STRING.chars()
                .collect((Supplier<Map<Integer, Set<Integer>>>) HashMap::new,
                        (map, value) -> map.putIfAbsent(value, locations(input, value)),
                        Map::putAll);
    }

    private Set<Integer> locations(String input, int x) {
        Set<Integer> locations = new HashSet<>();
        char[] chars = input.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == x) locations.add(i);
        }

        return locations;
    }

    public static void main(String[] args) {
        CodeJam codeJam = new CodeJam();
        Scanner sc = new Scanner(System.in);
        int numberOfTests = sc.nextInt();

        List<String> output = new ArrayList<>();
        sc.nextLine();

        for (int i = 0; i < numberOfTests; i++) {
            String s = sc.nextLine();
            output.add(String.format("Case #%d: %s", (i + 1), codeJam.solve(s.toLowerCase())));
        }

        output.forEach(System.out::println);

        sc.close();
    }

    class Item {
        public Item(int indexInSubstring, int indexInMainString) {
            this.indexInSubstring = indexInSubstring;
            this.indexInMainString = indexInMainString;
        }

        private int indexInSubstring;
        private int indexInMainString;
    }
}
