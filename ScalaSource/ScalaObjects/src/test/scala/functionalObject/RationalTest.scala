/**
 *
 * @author: tyagig
 */

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import functionalObject.Rational

@RunWith(classOf[JUnitRunner])
class RationalSuite extends FunSuite {
  var r1 = new Rational(1,3)
  var r2 = new Rational(2,5)

  test("Rational: 1/3 + 1/5 to give 11/15") {
    var result = r1+r2
    assert(result.numer == 11)
    assert(result.denom == 15)
  }
}