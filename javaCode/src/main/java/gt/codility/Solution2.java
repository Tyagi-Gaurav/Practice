package gt.codility;

import java.util.Optional;

public class Solution2 {
    public boolean solution(String input, String[] comparisonStrings) {

        int inputLength = input.length();
        int csCount = 0;
        boolean isAnagram = false;

        while (csCount < comparisonStrings.length && !isAnagram) {
            String candidateAnagram = comparisonStrings[csCount];
            int comparisonStringLength = Optional.ofNullable(candidateAnagram).orElse("").length();

            if (inputLength == comparisonStringLength) {
                int result = 0;

                for (int i = 0; i < inputLength; i++) {
                    result = result ^ input.charAt(i);
                    result = result ^ candidateAnagram.charAt(i);
                }

                if (result == 0) {
                    isAnagram = true;
                }
            }

            ++csCount;
        }

        return isAnagram;
    }

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        System.out.println(solution2.solution("Trees", new String[] {"Stere", "Cars", "Ardvark"}));
        System.out.println(solution2.solution("Trees", new String[] {"STEER", "Cars", "Ardvark"}));
        System.out.println(solution2.solution("TREES", new String[] {"sTeER", "Cars", "Ardvark"}));
        System.out.println(solution2.solution("Listen", new String[] {"Silent", "Cars", "Ardvark"}));
        System.out.println(solution2.solution("Listen", new String[] {"Silenc", "Cars", "Ardvark"}));
    }
}
