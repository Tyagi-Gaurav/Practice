package gt.practice.java8.functionalWrapper;

import java.util.function.Function;
import java.util.stream.Stream;

public class Wrapper {
    public static void main(String[] args) {
        Stream.of(1, 2, 3)
        .map(wrapper(Wrapper::methodWithException));
    }

    private static Integer methodWithException(Integer integer) throws Exception {
        return null;
    }

    private static <T, R, E extends Exception>
        Function<T,R> wrapper(WrapperInterface<T,R,E> wi) {
            return arg -> {
                try {
                    return wi.apply(arg);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
    }

}
