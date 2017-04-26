import org.scalatest.FunSuite

import prisonEscape._

class pescapeSuite extends FunSuite {

  test("small jump 1") {
    assert(pescape.calculateJumps(10, 1, Array(10)) == 1)
  }

  test("small jump 2") {
    assert(pescape.calculateJumps(5, 1, Array(9, 10)) == 5)
  }
}
