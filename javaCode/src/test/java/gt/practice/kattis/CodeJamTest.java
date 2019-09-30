package gt.practice.kattis;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class CodeJamTest {
    public CodeJam codeJam = new CodeJam();

    @ParameterizedTest
    @MethodSource("provideInputForTest")
    void name(String input, String expectedOutput) {
        assertThat(codeJam.solve(input)).isEqualTo(expectedOutput);
    }

    private static Stream<Arguments> provideInputForTest() {
        return Stream.of(
                Arguments.of("elcomew elcome to code jam", "0001"),
                Arguments.of("wweellccoommee to code qps jam", "0256"),
                Arguments.of("welcome to codejam", "0000")
        );
    }
}