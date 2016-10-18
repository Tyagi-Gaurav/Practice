/**
 * Created by gauravt on 26/01/15.
 */
import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Before
import org.junit.Test


class InsertionSortTest extends JUnitSuite {

  val f_Int_Ascend = (x:Int,y:Int) => if (x < y) true else false
  val f_Int_Descend = (x:Int,y:Int) => !f_Int_Ascend(x,y)

  @Test def shouldSortUnsortedArrayInAscending() {
    assertEquals(List(1,2,3,4,5), InsertionSort.sort(List(5,4,3,2,1), f_Int_Ascend))
  }

  @Test def shouldReturnSortedArrayAsIs() {
    assertEquals(List(1,2,3,4,5), InsertionSort.sort(List(1,2,3,4,5), f_Int_Ascend))
  }

  @Test def shouldSortUnsortedArrayInDescending() {
    assertEquals(List(5,4,3,2,1), InsertionSort.sort(List(1,2,3,4,5), f_Int_Descend))
  }
}
