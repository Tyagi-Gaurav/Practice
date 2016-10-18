
import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class QueueTestSuite extends FunSuite {
  test("Should be able to create a queue") {
    val q = Queue(1,2,3)
    assert(q.head == 1)
  }

  test("Insert random elements into queue and access head ") {
    val q = Queue().
      enqueue(5).
      enqueue(3).
      enqueue(1)
    
    assert(q.head == 5)
    
  }

  test("Insert random elements into queue and access tail ") {
    val q = Queue().
      enqueue(5).
      enqueue(3).
      enqueue(1).
      enqueue(7).
      enqueue(4)
    

    assert(q.tail.head == 3)
  }
}
