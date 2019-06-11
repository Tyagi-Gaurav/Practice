package gt.datastructures;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Theories.class)
public class BalancingParenthesisTest {
    private BalancingParenthesis parenthesis = new BalancingParenthesis();

    @Theory
    public void name(Pair pair) {
        assertThat(parenthesis.checkIfBalancedWithStack(pair.getInput()))
                .isEqualTo(pair.getExpected());
    }

    @DataPoints
    public static Pair[] testValues = {
            new Pair("()", -1),
            new Pair("()(", 2),
            new Pair("())", 2),
            new Pair(")()(", 0),
            new Pair("((())())()", -1),
            new Pair("((()))))()", 6),
            new Pair("(()((())()))", -1),
            new Pair("()))", 2),
            new Pair("(()()(()", 7)
    };

    static class Pair {
        private String input;
        private int expected;

        public Pair(String input, int expected) {
            this.input = input;
            this.expected = expected;
        }

        public String getInput() {
            return input;
        }

        public int getExpected() {
            return expected;
        }
    }
}
