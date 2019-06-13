package gt.datastructures;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BinaryTreeTest {

    private BinaryTree<Integer> binaryTree;

    @Test
    public void checkInOrderTraversal() throws Exception {
        binaryTree = new BinaryTree<>(15);
        //Given
        for (int i = 1; i <= 4; i++) {
                binaryTree.add(15 - i);
                binaryTree.add(15 + i);
        }

        //When
        List<Integer> result = binaryTree.inOrderTraversal();

        //Then
        assertThat(result).isEqualTo(Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 19));
    }

    @Test
    public void checkPreOrderTraversal() throws Exception {
        binaryTree = new BinaryTree<>(15);
        //Given
        for (int i = 1; i <= 4; i++) {
            binaryTree.add(15 - i);
            binaryTree.add(15 + i);
        }

        //When
        List<Integer> result = binaryTree.preOrderTraversal();

        //Then
        assertThat(result).isEqualTo(Arrays.asList(15, 14, 13, 12, 11, 16, 17, 18, 19));
    }

    @Test
    public void checkPostOrderTraversal() throws Exception {
        binaryTree = new BinaryTree<>(15);
        //Given
        for (int i = 1; i <= 4; i++) {
            binaryTree.add(15 - i);
            binaryTree.add(15 + i);
        }

        //When
        List<Integer> result = binaryTree.postOrderTraversal();

        //Then
        assertThat(result).isEqualTo(Arrays.asList(11, 12, 13, 14, 19, 18, 17, 16, 15));
    }
}