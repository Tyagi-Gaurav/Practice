import org.scalatest.WordSpec

import longestNonDecreasing.NonDecSequen

class NonDecSequenSpec extends WordSpec {
  "should return length of longest sequence of non decreasing numbers for (1, 5, 3, 7, 9, 11, 8)" in {
    val inputA = Array(1, 5, 3, 7, 9, 11, 8)
    val output = NonDecSequen.longestNonDecreasingSeq(inputA)

    assert(output.length == 5)
  }

  "should return longest sequence of non decreasing numbers for (1, 5, 3, 7, 9, 11, 8)" in {
    val inputA = Array(1, 5, 3, 7, 9, 11, 8)
    val output = NonDecSequen.longestNonDecreasingSeq(inputA)

    assert(output.deep == Array(1, 5, 7, 9, 11).deep)
  }
}
