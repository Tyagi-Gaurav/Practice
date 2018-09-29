package gt.practice.kattis;

import java.util.*;

public class Parking {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            List<Integer> price = new ArrayList<>();
            price.add(scanner.nextInt());
            price.add(scanner.nextInt());
            price.add(scanner.nextInt());

            Map<String, Integer> score = new HashMap<>();

            for (int i = 0; i < 3; i++) {
                int startTime = scanner.nextInt();
                int endTime = scanner.nextInt();

                List<String> buckets = convertToBuckets(startTime, endTime);

                buckets.stream()
                        .forEach(x -> score.compute(x, (k, v) -> v == null ? 1 : v+1));
            }

            System.out.println(score.entrySet().stream()
                    .mapToInt(x -> x.getValue() * price.get(x.getValue() - 1))
                    .sum());
        }
    }

    private static List<String> convertToBuckets(int startTime, int endTime) {
        List<String> output = new ArrayList<>();
        for (int i = startTime; i < endTime; i++) {
            output.add(i + "," + (i+1));
        }

        return output;
    }
}
