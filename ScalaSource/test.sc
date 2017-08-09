val input = Array( 70, 55, 13, 2, 99, 2, 80, 80, 80, 80, 100, 19, 7, 5, 5, 5, 1000, 32, 32)

val newInput = input.toList.sliding(2).collect{
  case Seq(a, b) if (a != b) => b
}.toArray

val newInput1 = input.zipWithIndex.filter(x => (x._2 == 0 || x._1 != input(x._2 - 1))).map(_._1)

case class test(s : String)

object testMe {

  def apply(s : String) : test = {
    test(s)
  }
}

testMe("abc").s