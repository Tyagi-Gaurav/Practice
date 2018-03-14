package gt.practice.java8.optional;

import java.util.Optional;

public class OptionalCode {
    public static void main(String[] args) {
        Optional<Optional<Boolean>> opt_boolean_1 = Optional.ofNullable(Optional.ofNullable(true));
        Optional<Optional<Boolean>> opt_boolean_2 = Optional.ofNullable(Optional.ofNullable(null));

        System.out.println(opt_boolean_1.flatMap(x -> x).orElse(false));
        System.out.println(opt_boolean_2.flatMap(x -> x).orElse(false));
    }
}
