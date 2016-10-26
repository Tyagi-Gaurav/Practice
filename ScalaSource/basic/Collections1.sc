
def getMapWithMutableMap(x : String) : scala.collection.mutable.Map[Char, Set[Int]] = {
  var indexMap = scala.collection.mutable.Map.empty[Char, Set[Int]]

  for (i<- 0 until x.length) {
    var y = indexMap.getOrElse(x.charAt(i), Set.empty[Int]) + i
    indexMap += x(i) -> y
  }

  indexMap
}

getMapWithMutableMap("Mississippi")

def getUsingImmutableMap(x : String) : Map[Char, Set[Int]] = {
  val charList = x.toCharArray

  def doWork(i : Int, startMap : Map[Char, Set[Int]]) : Map[Char, Set[Int]] = {
    if (i < x.length) {
      val a = startMap.getOrElse(x(i), Set.empty[Int]) + i
      doWork(i+1, startMap + (x(i) -> a))
    }
    else
      startMap
  }

  doWork(0, Map.empty[Char, Set[Int]])
}

getUsingImmutableMap("Mississippi")

val x : Map[String, String] = Map("a" -> "b") ++ Map("c" -> "d")

val y : Map[String, String] = x ++ Map("e" -> "f")
