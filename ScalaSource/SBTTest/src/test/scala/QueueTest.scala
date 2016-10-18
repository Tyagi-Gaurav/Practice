/**
 * Created by gauravt on 26/01/15.
 */

import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Before
import org.junit.Test

class QueueTest extends JUnitSuite {
  var q : aQueue[Int] = null

  @Before def setUp() {
    q = new NEQueue[Int]()
  }

  @Test def shouldAddToQueue() {
    q.enqueue(1)
    assertTrue(!q.isEmpty)
    assertEquals(Some(1), q.peek)
  }

  @Test def shouldRemoveFromQueue() {
    q.enqueue(1)
    q.enqueue(2)
    q.enqueue(3)
    assertTrue(!q.isEmpty)
    assertEquals(Some(1), q.peek)
    assertEquals(Some(1), q.dequeue)
    assertEquals(Some(2), q.dequeue)
  }

  @Test def shouldReturnNoneWhenRemovingFromEmptyQueue() {
    assertTrue(q.isEmpty)
    assertEquals(None, q.dequeue)
  }

  @Test def shouldWorkAsFIFO() {
    assertTrue(q.isEmpty)
    q.enqueue(3)
    q.enqueue(4)
    q.enqueue(5)
    assertTrue(!q.isEmpty)
    assertEquals(Some(3), q.dequeue)
    assertEquals(Some(4), q.dequeue)
    q.enqueue(6)
    assertEquals(Some(5), q.dequeue)
    assertEquals(Some(6), q.dequeue)
    assertTrue(q.isEmpty)
    assertEquals(None, q.dequeue)
  }
}
