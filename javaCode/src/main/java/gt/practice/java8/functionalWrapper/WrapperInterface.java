package gt.practice.java8.functionalWrapper;

@FunctionalInterface
public interface WrapperInterface<P, R, E extends Exception> {
    R apply(P t) throws E;
}
