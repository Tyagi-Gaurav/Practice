package gt.practice.algos.arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordLadder {
    public static void main(String[] args) {
        String[] dict = {"hot","dot","dog","lot","log"};
        System.out.println(getTransformationLength("hit", "cog", new HashSet<>(Arrays.asList(dict))));
        System.out.println(getTransformationLength("hit", "lag", new HashSet<>(Arrays.asList(dict))));
    }

    private static int getTransformationLength(String start, String end, Set<String> dict) {
        BFSNode<String> head = new BFSNode(start.toLowerCase());
        return calculateTransformation(head, end, dict);
    }

    private static int calculateTransformation(BFSNode<String> node, String end, Set<String> dict) {
        String nodeData = node.getNodeData();
        for (int i=0; i < nodeData.length(); ++i) {
            for (int j=97;j < 112;++j) {
                if (j != nodeData.charAt(i)) {
                    String newWord = nodeData.substring(0, i) + (char)j + nodeData.substring(i+1);
                    if (end.equals(newWord)) {
                        return 2;
                    }

                    if (dict.contains(newWord) && !node.sameAsParentData(newWord) )  {
                        node.addNode(newWord);
                        dict.remove(newWord);
                    }
                }
            }
        }

        List<BFSNode<String>> childNodes = node.getChildNodes();
        if (childNodes != null) {
            for (BFSNode<String> childNode : childNodes) {
                int transformLength = calculateTransformation(childNode, end, dict);
                if (transformLength > 0) {
                    return transformLength+1;
                }
            }
        }

        return 0;
    }
}
