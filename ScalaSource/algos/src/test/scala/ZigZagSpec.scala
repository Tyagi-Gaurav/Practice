import org.scalatest.WordSpec

import longestNonDecreasing.zigzag

class ZigZagSpec extends WordSpec {
  "should return zig zag sequence for test case 1" in {
    val input = Array(1, 7, 4, 9, 2, 5)
    val output = zigzag.longestZigZag(input)

    assert(output == 6)
  }

  "should return zig zag sequence for test case 2" in {
    val input = Array(1, 17, 5, 10, 13, 15, 10, 5, 16, 8)
    val output = zigzag.longestZigZag(input)

    assert(output == 7)
  }

  "should return zig zag sequence for test case 3" in {
    val input = Array(44)
    val output = zigzag.longestZigZag(input)

    assert(output == 1)
  }

  "should return zig zag sequence for test case 4" in {
    val input = Array( 1, 2, 3, 4, 5, 6, 7, 8, 9 )
    val output = zigzag.longestZigZag(input)

    assert(output == 2)
  }

  "should return zig zag sequence for test case 5" in {
    val input = Array( 70, 55, 13, 2, 99, 2, 80, 80, 80, 80, 100, 19, 7, 5, 5, 5, 1000, 32, 32)

    val output = zigzag.longestZigZag(input)

    assert(output == 8)
  }

  "should return zig zag sequence for test case 6" in {
    val input = Array( 374, 40, 854, 203, 203, 156, 362, 279, 812, 955,
      600, 947, 978, 46, 100, 953, 670, 862, 568, 188,
    67, 669, 810, 704, 52, 861, 49, 640, 370, 908,
    477, 245, 413, 109, 659, 401, 483, 308, 609, 120,
    249, 22, 176, 279, 23, 22, 617, 462, 459, 244)

    val output = zigzag.longestZigZag(input)

    assert(output == 36)
  }

}
