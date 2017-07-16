
abstract class Element {
  def contents: Array[String]
  def height : Int = contents.length
  def width : Int = if (height == 0) 0 else contents(0).length
}

class ArrayElement(val contents : Array[String]) extends Element {
}

object ArrayElement extends Application {
  val sa = Array("abc1", "def")
  val a = new ArrayElement(sa)
  println (a.height)
  println (a.width)
}
