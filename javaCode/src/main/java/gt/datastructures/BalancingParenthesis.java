package gt.datastructures;

import java.util.Stack;

public class BalancingParenthesis {
    private Stack<Character> stack = new Stack<>();

    public int checkIfBalancedWithStack(String parenthesis) {
        char[] chars = parenthesis.toCharArray();
        int position = 0;

        for (char aChar : chars) {
            if (aChar == '(') {
                stack.push(aChar);
            } else if (aChar == ')') {
                if (stack.size() > 0) {
                    stack.pop();
                } else {
                    return position;
                }
            }

            position++;
        }

        if (stack.size() == 0) {
            return -1;
        }

        return position-1;
    }
}
