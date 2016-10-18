/**
 * Created by gauravt on 29/01/15.
 */

import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Test

class FastQueueTest extends JUnitSuite {
  @Test def shouldThrowExceptionWhenExtractHeadOfEmptyQueue() {
    try {
      val fq = FastQueue[Int]()
    } catch {
      case e : NoSuchElementException => //Expected
    }
  }

  @Test def shouldExtractHeadWhenQueueHasSingleElement() {
    val fq = FastQueue[Int](1)
    assertEquals(1, fq.head)
  }

  @Test def shouldEnqueueElementsAfterInitialUpload() {
    val fq = FastQueue[Int](1,2,3)
    fq.enqueue(4)
    fq.enqueue(5)
    assertEquals(1, fq.head)
    val tail = fq.tail
    assertEquals(2, tail.head)
  }
}
