/**
 * Created by gauravt on 28/01/15.
 */

import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Test

class BinarySearchTreeTest extends JUnitSuite {
  val f = ((x : Int,y: Int) => if (x > y) 1 else if (x == y) 0 else -1)

  @Test def shouldCreateASingleNode() {
    val n = Node[Int](2)
    assertEquals(Some(2), n.getValue)
  }

  @Test def shouldCreateARootWithLeftAndRightChilds() {
    val n = Node[Int](3)
    val newTree = n.add(4, f).add(5, f).add(1, f)
    assertEquals(Some(3), newTree.getValue)
  }

  @Test def shouldCreateATreeWithOnlyLeft() {
    val n = Node[Int](3)
    val newTree = n.add(2, f).add(1, f)
    assertEquals(List(1,2,3), newTree.inOrderTraverse)
  }

  @Test def shouldCreateATreeWithbothLeftAndRight() {
    val n = Node[Int](3)
    val newTree = n.add(2, f).add(1, f).add(4, f).add(10,f).add(8, f)
    assertEquals(List(1,2,3,4,8,10), newTree.inOrderTraverse)
  }

  @Test def shouldSearchAndReturnAnItemPresentInTheTree() {
    val n = Node[Int](3)
    val newTree = n.add(2, f).add(1, f).add(4, f).add(10,f).add(8, f)
    assertEquals(true, newTree.search(4, f))
  }

  @Test def shouldSearchAndReturnAnItemNotPresentInTheTree() {
    val n = Node[Int](3)
    val newTree = n.add(2, f).add(1, f).add(4, f).add(10,f).add(8, f)
    assertEquals(false, newTree.search(7, f))
  }
}
