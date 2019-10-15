package gt.practice.kattis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CardTrick {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfInputs = scanner.nextInt();
        List<List<Integer>> outputs = new ArrayList<>();

        for (int i = 0; i < numberOfInputs; i++) {
            int nextInput = scanner.nextInt();

            outputs.add(find(nextInput));
        }

        outputs.forEach(output -> {
            System.out.println(output.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(" ")));
        });
    }

    private static List<Integer> find(int nextInput) {
        List<Integer> output = new ArrayList<>();

        output.add(nextInput);
        nextInput --;

        while (nextInput > 0) {
            output.add(0, nextInput);
            rotate(output, nextInput);
            nextInput--;
        }

        return output;
    }

    private static void rotate(List<Integer> output, int nextInput) {
        int last = output.size() - 1;
        while (nextInput > 0) {
            Integer remove = output.remove(last);
            output.add(0, remove);
            nextInput--;
        }

    }
}
