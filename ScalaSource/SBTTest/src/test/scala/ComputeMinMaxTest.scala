/**
 * Created by gauravt on 23/01/15.
 */

import org.junit.Assert._
import org.junit.Test
import org.junit.Before
import org.scalatest.junit.JUnitSuite


class ComputeMinMaxTest extends JUnitSuite {

  @Test def shouldReturnMax() {
    assertEquals(5, ComputeMinMax.max(List(1,2,3,4,5)))
  }

  @Test def shouldReturnMin() {
    assertEquals(1, ComputeMinMax.min(List(1,2,3,4,5)))
  }

  @Test def shouldReturnMinWithFunction() {
    assertEquals(1, ComputeMinMax.compute(List(1,2,3,4,5), Integer.MAX_VALUE, (x:Int,y:Int) => if (x < y) x else y))
  }

  @Test def shouldReturnMaxWithFunction() {
    assertEquals(5, ComputeMinMax.compute(List(1,2,3,4,5), Integer.MIN_VALUE, (x:Int,y:Int) => if (x > y) x else y))
  }

  @Test def shouldReturnSumWithFunction() {
    assertEquals(15, ComputeMinMax.compute(List(1,2,3,4,5), 0, (x:Int,y:Int) => x+y))
  }
}
