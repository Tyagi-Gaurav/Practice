package gt.practice.kattis;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WelcomeToCodeJam {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int p = scanner.nextInt();
        int num = 0;
        String subSequence = "welcome to code jam";
        Set<Character> subSequenceSet = subSequence
                .chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
        scanner.nextLine();
        List<Integer> output = new ArrayList<>();

        while (num < p) {
            String input = scanner.nextLine().toLowerCase();
            int mainIndex = 0;
            int subIndex = 0;

            List<temp> initialized = new ArrayList<>();

            while (mainIndex < input.length()) {
                char currentChar = input.charAt(mainIndex);
                if (subSequenceSet.contains(currentChar)) {
                    initialized.add(new temp(currentChar, new ArrayList<>()));
                    initialized.get(subIndex).addIndex(subIndex);
                    subIndex++;
                }

                ++mainIndex;
            }

            Map<Character, List<Integer>> allLetters = initialized.stream()
                    .collect(Collectors.toMap(temp -> temp.ch, temp::getIndex, (list, list2) -> {
                        list.addAll(list2);
                        return list;
                    }));

            /*
                Create tree.
                Dummy root node.
                For each character in the subSequence, find the indexes that can be used and add them as child nodes.
             */
            Node root = new Node('$', -1, -1, new ArrayList<>(), true);
            Queue<Node> queue = new LinkedList<>();
            queue.offer(root);
            root.setMult(1L);
            for (int i = 0; i < subSequence.length(); i++) {
                char key = subSequence.charAt(i);
                List<Integer> indexes = allLetters.get(key);

                Queue<Node> tempQueue = new LinkedList<>();

                while (!queue.isEmpty()) {
                    Node nextNode = queue.poll();
                    List<Integer> count = count(indexes, nextNode.index);
                    int finalI = i;

                    count.forEach(index -> {
                        Node child = new Node(key, index, finalI, new ArrayList<>());
                        nextNode.addChild(child);
                        tempQueue.offer(child);
                    });
                }

                queue.addAll(tempQueue);
            }


            /*
                Do a DFS on the tree to search for different results.
             */
            Stack<Node> stack = new Stack<>();
            for (Node node : root.child) {
                stack.push(node);
            }

            long sum = 0L;
            while (!stack.isEmpty()) {
                Node node = stack.pop();

                List<Node> childNodes = node.child;

                childNodes.forEach(childNode -> stack.push(childNode));
            }


            System.out.println(allLetters);
            System.out.println(count(allLetters.get('e'), 11));

            ++num;
        }

        IntStream.range(0, output.size())
                .forEach(x -> System.out.println("Case #" + (x + 1) + ": " + lastFour(output.get(x))));
    }

    private static List<Integer> count(List<Integer> indexes, int targetIndex) {
        return indexes.stream().filter(x -> x > targetIndex).collect(Collectors.toList());
    }

    private static String lastFour(Integer integer) {
        System.out.println(integer);
        return String.format("%04d", integer % 10000);
    }

    static class temp {
        char ch;
        List<Integer> index;

        temp(char ch, List<Integer> index) {
            this.ch = ch;
            this.index = index;
        }

        public List<Integer> getIndex() {
            return index;
        }

        void addIndex(int c) {
            if (index == null) {
                index = new ArrayList<>();
            }
            index.add(c);
        }

        @Override
        public String toString() {
            return "temp{" +
                    "ch=" + ch +
                    ", index=" + index +
                    '}';
        }
    }

    static class Node {
        char data;
        int index;
        int subPosition;
        List<Node> child;
        long mult;
        boolean isRoot;

        public Node(char data, int index, int subPosition, List<Node> child) {
            this(data, index, subPosition, child, false);
        }

        public Node(char data, int index, int subPosition, List<Node> child, boolean isRoot) {
            this.data = data;
            this.index = index;
            this.subPosition = subPosition;
            this.child = child;
            this.isRoot = isRoot;
        }

        public void addChild(Node node) {
            child.add(node);
        }

        public void setMult(long result) {
            this.mult = result;
        }
    }
}
