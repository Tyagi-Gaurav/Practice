package ee.test;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;

public class FizzBuzzTest {

    private FizzBuzz fizzBuzz;

    @Before
    public void setUp() throws Exception {
        fizzBuzz = new FizzBuzz();
    }

    @Test
    public void printFizzWhenNumberMultipleOf3() throws Exception {
        // When
        String output = fizzBuzz.printFizzBuzzForNumbersBetween(6, 9);

        // Then
        assertThat(output).isEqualTo("fizz 7 8 fizz");
    }

    @Test
    public void printBuzzWhenNumberMultipleOf5() throws Exception {
        // When
        String output = fizzBuzz.printFizzBuzzForNumbersBetween(1, 5);

        // Then
        assertThat(output).isEqualTo("1 2 lucky 4 buzz");
    }

    @Test
    public void printFizzBuzzWhenNumberMultipleOf15() throws Exception {
        // When
        String output = fizzBuzz.printFizzBuzzForNumbersBetween(10, 15);

        // Then
        assertThat(output).isEqualTo("buzz 11 fizz lucky 14 fizzbuzz");
    }

    @Test
    public void printLuckyWhenNumberContainsA3() throws Exception {
        // When
        String output = fizzBuzz.printFizzBuzzForNumbersBetween(3, 14);

        // Then
        assertThat(output)
                .isEqualTo("lucky 4 buzz fizz 7 8 fizz buzz 11 fizz lucky 14");
    }

    @Test
    public void printFizzBuzzCorrectlyForABiggerRange() throws Exception {
        // When
        String output = fizzBuzz.printFizzBuzzForNumbersBetween(1, 50);

        // Then
        String outString = output;
        String[] split = outString.split(" ");
        List<String> strings = asList(split);

        Map<String, Integer> collect = groupOutputByKeys(strings);

        assertThat(collect.get("fizz")).isEqualTo(9);
        assertThat(collect.get("buzz")).isEqualTo(6);
        assertThat(collect.get("fizzbuzz")).isEqualTo(2);
        assertThat(collect.get("Integer")).isEqualTo(19);
        assertThat(collect.get("lucky")).isEqualTo(14);
    }

    @Test
    public void printFizzBuzzCorrectlyWhenRangeIncludesNegativeNumbers() throws Exception {
        // When
        String output = fizzBuzz.printFizzBuzzForNumbersBetween(-49, 50);

        // Then
        String outString = output;
        String[] split = outString.split(" ");
        List<String> strings = asList(split);

        Map<String, Integer> collect = groupOutputByKeys(strings);

        assertThat(collect.get("fizz")).isEqualTo(22);
        assertThat(collect.get("buzz")).isEqualTo(12);
        assertThat(collect.get("fizzbuzz")).isEqualTo(6);
        assertThat(collect.get("Integer")).isEqualTo(46);
        assertThat(collect.get("lucky")).isEqualTo(14);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentWhenMinRangeGreaterThanMaxRange() throws Exception {
        // When
        try {
            fizzBuzz.printFizzBuzzForNumbersBetween(49, -50);
            fail("Expected Exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Invalid Range Specified");
            throw e;
        }
    }

    private Map<String, Integer> groupOutputByKeys(List<String> strings) {
        return strings.stream().flatMap(str -> Stream.of(new TestKeyValue(str, 1)))
                .collect(
                        Collectors.groupingBy(
                                TestKeyValue::getKey,
                                Collectors.reducing(
                                        0,
                                        TestKeyValue::getValue,
                                        Integer::sum)));
    }

    private class TestKeyValue {
        private final String key;
        private final int value;

        public TestKeyValue(String key, int value) {
            if (isNumber(key)) {
               key = "Integer";
            }

            this.key = key;
            this.value = value;
        }

        private boolean isNumber(String key) {
            try {
                Integer.parseInt(key);
            } catch (NumberFormatException e) {
                return false;
            }

            return true;
        }

        public String getKey() {
            return key;
        }

        public int getValue() {
            return value;
        }
    }


}