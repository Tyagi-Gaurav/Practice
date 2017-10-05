package gt.practice.fizzBuzz;

@FunctionalInterface
public interface Converter<S, R> {
    R convert(S from);
}
