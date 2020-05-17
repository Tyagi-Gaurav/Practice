package gt.practice.hackerRank.problemsolving;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class MigratoryBirds {
    // Complete the migratoryBirds function below.
    static int migratoryBirds(List<Integer> arr) {
        Map<Integer, Long> collect = arr.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Optional<Map.Entry<Integer, Long>> max = collect.entrySet()
                .stream().max(Comparator.comparing(Map.Entry::getValue));

        Map.Entry<Integer, Long> integerLongEntry = max.get();

        Long maxValue = integerLongEntry.getValue();

        int shortestId = Integer.MAX_VALUE;
        for (Integer key : collect.keySet())  {
            if (collect.get(key).equals(maxValue)) {
                if (key < shortestId) {
                    shortestId = key;
                }
            }
        }

        return shortestId;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int arrCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(toList());

        int result = migratoryBirds(arr);

        System.out.println(result);

        bufferedReader.close();
    }
}
