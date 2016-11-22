package gt.practice.algos.arrays;

import java.util.HashMap;
import java.util.Map;

public class IsomorphicStrings {
    public static void main(String[] args) {
        System.out.println(isIsoMorphic("foo", "bar"));
        System.out.println(isIsoMorphic("abca", "zbxz"));
        System.out.println(isIsoMorphic("cacccdaabc", "cdcccaddbc"));
    }

    private static boolean isIsoMorphic(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }

        return  isIsoMorphicDoWork(s1, s2) &&
                isIsoMorphicDoWork(s2, s1);
    }

    private static boolean isIsoMorphicDoWork(String s1, String s2) {
        Map<Character, Character> aToBMap = new HashMap<>();

        for (int i=0; i < s1.length(); ++i) {
            char key = s1.charAt(i);

            if (!aToBMap.containsKey(key)) {
                aToBMap.put(key, s2.charAt(i));
            } else {
                char value = aToBMap.get(key);
                if (value != s2.charAt(i)) {
                    return false;
                }
            }
        }

        return true;
    }
}
