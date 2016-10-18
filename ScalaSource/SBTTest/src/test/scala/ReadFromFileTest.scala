/**
 * Created by gauravt on 21/01/15.
 */

import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Test
import org.junit.Before

class ReadFromFileTest extends JUnitSuite {

  @Test def shouldReadFromFile() {
    val lines = ReadFromFile.read("/TestFile.txt")
    assertNotNull(lines)
    assertTrue(lines.size == 2)
    assertEquals(lines(0), "To be or not to be")
    assertEquals(lines(1), "Lets do it")
  }
}
