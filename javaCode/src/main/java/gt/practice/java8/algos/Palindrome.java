package gt.practice.java8.algos;

import java.util.*;

public class Palindrome {
    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", Arrays.asList("B", "C"));
        graph.put("B", Arrays.asList("C", "D"));
        graph.put("C", Arrays.asList("D"));
        graph.put("D", Arrays.asList("C"));
        graph.put("E", Arrays.asList("F"));
        graph.put("F", Arrays.asList("C"));

        System.out.println(Palindrome.findPath(graph, "A", "D", null));
    }

    public static List<String> findPath(Map<String, List<String>> graph,
                                 String start, String end, List<String> path) {
        if (path == null)
            path = new ArrayList<>();

        path.add(start);

        if (start == end)
            return path;

        for (String node : graph.get(start)) {
            if (!path.contains(node)) {
                List<String> newPath = findPath(graph, node, end, path);
                if (newPath != null) {
                    return newPath;
//                    path.addAll(newPath);
                }
            }
        }

        return path;
    }
}
