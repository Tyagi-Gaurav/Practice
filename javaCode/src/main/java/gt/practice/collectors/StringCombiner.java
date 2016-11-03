package gt.practice.collectors;

public class StringCombiner {
    private final String delimiter;
    private final String prefix;
    private final String suffix;
    private StringBuilder builder = new StringBuilder();

    public StringCombiner(String delimiter, String prefix, String suffix) {
        this.delimiter = delimiter;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public StringCombiner add(String element) {
        if (areAtStart()) {
            builder.append(prefix); }
        else{
            builder.append(delimiter);
        }

        builder.append(element);
        return this;
    }

    private boolean areAtStart() {
        return builder.length() == 0;
    }

    public StringCombiner merge(StringCombiner otherCombiner) {
        builder.append(otherCombiner.getBuilder());
        builder.append(suffix);
        return this;
    }

    public StringBuilder getBuilder() {
        return builder;
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
