package gt.datastructures;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree<T extends Comparable> {
    T node;
    BinaryTree<T> left;
    BinaryTree<T> right;

    public BinaryTree(T node) {
        this(node, null, null);
    }

    public BinaryTree(T node, BinaryTree<T> left, BinaryTree<T> right) {
        this.node = node;
        this.left = left;
        this.right = right;
    }

    public void add(T that) {
        if (this.node.compareTo(that) < 1) {
            if (this.right != null) {
                this.right.add(that);
            } else {
                this.right = new BinaryTree<>(that);
            }
        } else {
            if (this.left != null) {
                this.left.add(that);
            } else {
                this.left = new BinaryTree<>(that);
            }
        }
    }

    public List<T> inOrderTraversal() {
        List<T> result = new ArrayList<>();

        if (this.left != null) {
            result.addAll(this.left.inOrderTraversal());
        }
        result.add(this.node);

        if (this.right != null) {
            result.addAll(this.right.inOrderTraversal());
        }
        return result;
    }


    public List<T> preOrderTraversal() {
        List<T> result = new ArrayList<>();

        result.add(this.node);
        if (this.left != null) {
            result.addAll(this.left.preOrderTraversal());
        }
        if (this.right != null) {
            result.addAll(this.right.preOrderTraversal());
        }
        return result;
    }

    public List<T> postOrderTraversal() {
        List<T> result = new ArrayList<>();

        if (this.left != null) {
            result.addAll(this.left.postOrderTraversal());
        }

        if (this.right != null) {
            result.addAll(this.right.postOrderTraversal());
        }
        result.add(this.node);
        return result;
    }
}
