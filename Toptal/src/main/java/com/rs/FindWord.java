package com.rs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FindWord {
    public String find(String[] precedenceRules) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> hashMap = new HashMap<>();
        Set<String> firstLetterSet = new HashSet<>();

        for (int i = 0; i < precedenceRules.length; i++) {
            String[] split = precedenceRules[i].split(">");
            String part2 = split[1];
            String part1 = split[0];

            firstLetterSet.add(part1);
            firstLetterSet.remove(part2);

            hashMap.put(part1, part2);
        }

        String nextKey = firstLetterSet.iterator().next();
        stringBuilder.append(nextKey);

        while (hashMap.get(nextKey) != null) {
            String value = hashMap.get(nextKey);
            stringBuilder.append(value);
            nextKey = value;
        }

        return stringBuilder.toString();
    }
}
