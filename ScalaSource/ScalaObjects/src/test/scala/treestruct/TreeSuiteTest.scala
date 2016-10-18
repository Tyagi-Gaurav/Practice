/**
 *
 * @author: tyagig
 */

import treestruct._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TreeSuiteTest extends FunSuite {
  var testTree = new NonEmptyTree(9, new NonEmptyTree(5, new EmptyTree, new EmptyTree), new NonEmptyTree(15, new EmptyTree, new EmptyTree))

  test("Add one node into the tree") {
    var newTree = testTree.add(5)
    assert(newTree.contains(5))
  }

  test("Should contain all nodes") {
    assert(testTree.contains(9))
    assert(testTree.contains(5))
    assert(testTree.contains(15))
    assert(!testTree.contains(20))
  }

  test("Add two node into the tree") {
    var newTree1 = testTree.add(5)
    var newTree2 = newTree1.add(20)
    assert(newTree2.contains(5))
    assert(newTree2.contains(20))
  }

  test("Join 2 trees together") {
    var newTree = new NonEmptyTree(19, new NonEmptyTree(6, new EmptyTree, new EmptyTree), new NonEmptyTree(25, new EmptyTree, new EmptyTree))
    var joinedTree = testTree.join(newTree)
    println("Joined Tree" + joinedTree)
    assert(joinedTree.contains(19))
    assert(joinedTree.contains(25))
    assert(joinedTree.contains(5))
    assert(joinedTree.contains(6))
    assert(joinedTree.contains(9))
    assert(joinedTree.contains(15))
    assert(!joinedTree.contains(20))
  }

  test("Filter nodes greater than 6") {
    var newTree = testTree.filter(x => x > 6)
    assert(newTree.contains(15))
    assert(newTree.contains(9))
    assert(!newTree.contains(5))
  }
}

