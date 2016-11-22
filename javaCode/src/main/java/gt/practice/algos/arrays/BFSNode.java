package gt.practice.algos.arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BFSNode<T> {
    private T nodeData;
    private BFSNode<T> parentNode;
    private List<BFSNode<T>> childNodes;

    public BFSNode(T nodeData) {
        this(nodeData, null);
    }

    BFSNode(T nodeData, BFSNode<T> parentNode) {
        this.nodeData = nodeData;
        this.parentNode = parentNode;
        this.childNodes = null;
    }

    public void addNode(T node) {
        if (childNodes == null) {
            childNodes = new ArrayList<>();
        }

        childNodes.add(new BFSNode<T>(node, this));
    }

    public List<BFSNode<T>> getChildNodes() {
        return Collections.unmodifiableList(childNodes);
    }

    public T getNodeData() {
        return nodeData;
    }

    public boolean sameAsParentData(T newWord) {
        if (parentNode != null && parentNode.getNodeData().equals(newWord)) {
            return true;
        }

        return false;
    }
}
