package composAndInherit

/**
 *
 * @author: tyagig
 */
abstract class Element {
  def contents : Array[String]
  def height : Int = contents.length
  def width : Int = if (height == 0) 0 else contents(0).length
}

class ArrayElement(conts : Array[String]) extends Element {
  /*
  The uniform access principle is just one aspect where Scala treats fields and
  methods more uniformly than Java. Another difference is that in Scala, fields
  and methods belong to the same namespace. This makes it possible for a
  field to override a parameterless method.
   */
  val contents = conts
}

class lineElement(s : String) extends ArrayElement(Array(s)) {
  override def width : Int = s.length
  override def height = 1
}

object ArrayElement {
  def main(args : Array[String]) {
    val arrayEl = new ArrayElement(Array("hello", "world"))
    println (arrayEl.width)
  }

}