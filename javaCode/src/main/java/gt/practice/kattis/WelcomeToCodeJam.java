package gt.practice.kattis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Misc {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int p = scanner.nextInt();
        int num = 0;
        String subSequence = "welcome to code jam";
        scanner.nextLine();
        List<Integer> output = new ArrayList<>();

        while (num < p) {
            String input = scanner.nextLine();
            int mainIndex = 0;
            int subIndex = 0;

            List<temp> initialized = IntStream.range(0, subSequence.length())
                    .mapToObj(x -> new temp(subSequence.charAt(x), new ArrayList<>()))
                    .collect(Collectors.toList());

            while (mainIndex < input.length() && subIndex < subSequence.length()) {
                if (input.charAt(mainIndex) == subSequence.charAt(subIndex)) {
                    initialized.get(subIndex).addIndex(mainIndex);
                    ++mainIndex;
                } else {
                    if (initialized.get(subIndex).getIndex().size() > 0) {
                        if (subIndex + 1 < subSequence.length() &&
                                input.charAt(mainIndex) == subSequence.charAt(subIndex+1)) {
                            ++subIndex;
                        } else {
                            ++mainIndex;
                        }
                    } else {
                        ++mainIndex;
                    }
                }
            }

            //initialized.forEach(System.out::println);

            output.add(initialized
                    .stream()
                    .mapToInt(x -> x.getIndex().size())
                    .reduce(1, (left, right) -> left * right));

            ++num;
        }

        IntStream.range(0, output.size())
                .forEach(x -> System.out.println("Case #" + x + ": " + lastFour(output.get(x))));
    }

    private static String lastFour(Integer integer) {
        return String.format("%04d", integer % 1000);
    }

    static class temp {
        char ch;
        List<Integer> index;

        temp(char ch, List<Integer> index) {
            this.ch = ch;
            this.index = index;
        }

        public char getCh() {
            return ch;
        }

        public List<Integer> getIndex() {
            return index;
        }

        void addIndex(int c) {
            index.add(c);
        }

        @Override
        public String toString() {
            return "temp{" +
                    "ch=" + ch +
                    ", index=" + index +
                    '}';
        }
    }
}
