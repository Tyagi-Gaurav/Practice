package gt.codility;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Solution3 {
    Map<Long, Long> count(Map<String, UserStats>... visits) {

        if (visits != null) {
            return Arrays.stream(visits)
                    .filter(map -> map != null && !map.isEmpty())
                    .filter(this::isLong)
                    .filter(this::userStatsExist)
                    .map(userStatsMap -> userStatsMap.entrySet().iterator().next())
                    .collect(
                            Collectors.groupingBy(
                                    userStatsEntry -> Long.parseLong(userStatsEntry.getKey()),
                                    Collectors.summingLong(value -> value.getValue().getVisitCount().orElse(0L))));

        } else {
            return Collections.emptyMap();
        }

    }

    private boolean userStatsExist(Map<String, UserStats> userStatsMap) {
        Iterator<UserStats> values = userStatsMap.values().iterator();
        UserStats userStats = values.next();
        return userStats != null && userStats.getVisitCount().isPresent();
    }

    private boolean isLong(Map<String, UserStats> userStatsMap) {
        try {
            Iterator<String> strings = userStatsMap.keySet().iterator();
            String key = strings.next();
            Long.parseLong(key);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        Solution3 solution3 = new Solution3();
        System.out.println(solution3.count(null));
        System.out.println(solution3.count(Collections.emptyMap()));
        System.out.println(solution3.count(
                createMap("1", new UserStats(Optional.of(3L))),
                createMap("1", new UserStats(Optional.of(5L))),
                createMap("1", new UserStats(Optional.of(7L))),
                createMap(null, new UserStats(Optional.of(7L))),
                createMap("ddd", new UserStats(Optional.of(7L))),
                createMap("2", new UserStats(Optional.of(7L)))
        ));

        System.out.println(solution3.count(
                createMap("1", new UserStats(Optional.empty())),
                createMap("2", new UserStats(Optional.empty()))
        ));


    }

    private static Map<String, UserStats> createMap(String s, UserStats userStats) {
        HashMap<String, UserStats> map = new HashMap<>();
        map.put(s, userStats);
        return map;
    }
}
