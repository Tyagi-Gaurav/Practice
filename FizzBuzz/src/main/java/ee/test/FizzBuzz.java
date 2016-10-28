package ee.test;

public class FizzBuzz {
    public String printFizzBuzzForNumbersBetween(int rangeMin, int rangeMax) {
        StringBuilder builder = new StringBuilder();

        if (rangeMin > rangeMax) {
            throw new IllegalArgumentException("Invalid Range Specified");
        }

        for (int i=rangeMin; i <= rangeMax;++i) {
            if (numberContains(3, i)) {
                builder.append("lucky");
            } else if (isMultipleOf(15, i)) {
                builder.append("fizzbuzz");
            } else if (isMultipleOf(3, i)) {
                builder.append("fizz");
            } else if (isMultipleOf(5 , i)) {
                builder.append("buzz");
            } else {
                builder.append(i);
            }

            builder.append(" ");
        }

        return builder.toString().trim();
    }

    private boolean numberContains(int digit, int number) {
        while (number != 0) {
            if (number % 10 == digit) {
                return true;
            }
            number = number / 10;
        }

        return false;
    }

    private boolean isMultipleOf(int divisor, int dividend) {
        return dividend % divisor == 0;
    }

    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz();
        String output = fizzBuzz.printFizzBuzzForNumbersBetween(1, 20);
        System.out.println(output);
    }
}
