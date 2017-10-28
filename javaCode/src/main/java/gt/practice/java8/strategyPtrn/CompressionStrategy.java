package gt.practice.java8.strategyPtrn;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface CompressionStrategy {
    OutputStream compres(OutputStream data) throws IOException;
}
