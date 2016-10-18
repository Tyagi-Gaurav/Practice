/**
 * Created by gauravt on 31/01/15.
 */

import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Test

class UnionFindTest extends JUnitSuite {
  @Test def shouldInitialiseUnionOfElements() {
    val uf = UnionFind[Int](1,2,3)
    assertTrue(uf.contains(1))
    assertTrue(uf.contains(2))
    assertTrue(uf.contains(3))
    assertFalse(uf.contains(4))
  }

  @Test def shouldCreateUnionOfElements() {
    val uf = UnionFind[Int](1,2,3,4,5,6,7,8,9,10)
    val uf1 = uf.union(1,5)
    assertTrue(uf1.contains(1))
    assertTrue(uf1.contains(5))
    assertTrue(uf1.connected(1,5))
    assertFalse(uf1.connected(1,2))
    assertFalse(uf1.connected(1,3))
  }
}
