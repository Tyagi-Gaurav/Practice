package gt.practice.java8.collectors;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CustomStringCollector implements Collector<String, StringCombiner, String> {
    private final String prefix;
    private final String suffix;
    private final String delimiter;

    public CustomStringCollector(String prefix, String suffix, String delimiter) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.delimiter = delimiter;
    }

    @Override
    public Supplier<StringCombiner> supplier() {
        return () -> new StringCombiner(delimiter, prefix, suffix);
    }

    @Override
    public BiConsumer<StringCombiner, java.lang.String> accumulator() {
        return StringCombiner::add;
    }

    @Override
    public BinaryOperator<StringCombiner> combiner() {
        return StringCombiner::merge;
    }

    @Override
    public Function<StringCombiner, java.lang.String> finisher() {
        return StringCombiner::toString;
    }

    @Override
    public Set<Characteristics> characteristics() {
//        return Stream.of(Characteristics.CONCURRENT).collect(Collectors.toSet());
        return Collections.EMPTY_SET;
    }
}
