package dance

object partyDance extends App {
  val inputA = Array(
    "M1#W2#W4",
    "M2#W1#W2",
    "M3#W1#W3#W4",
    "M4#W4#W5",
    "M5#W4")

  val inputB = Array(
    "M1#W2#W4",
    "M2#W1#W2",
    "M3#W1#W3#W4",
    "M4#W4#W5",
    "M5")

  val inputC = Array(
    "M1#W4",
    "M2#W1#W2",
    "M3#W1#W3#W4",
    "M4#W4#W5",
    "M5#W4")

  val inputD = Array(
    "M1#W6#W10",
    "M2#W1#W5",
    "M3#W1#W3#W5#W9",
    "M4#W3#W4",
    "M5#W2#W6",
    "M6#W1#W2#W6",
    "M7#W1#W7#W8",
    "M8#W10#W8",
    "M9#W3#W9",
    "M10#W10")

  val ip1_size = scala.io.StdIn.readInt
  val ip1 = Array.ofDim[String](ip1_size)

  for (ip1_i <- 0 until ip1_size)
    ip1(ip1_i) = scala.io.StdIn.readLine

  println(doWork(ip1))
//  println(doWork(inputB))
//  println(doWork(inputC))
//  println(doWork(inputD))

  def createMapForAllWomen(inputA: Array[String]) : Map[String, Set[String]]= {
    inputA.map(x => x.split("#"))
          .flatMap(x => x.map(y=> (y , x(0))))
          .groupBy(_._1)
          .mapValues(x => x.map(y => y._2))
          .filter(x => x._1.startsWith("W"))
          .map(y => y._1 -> y._2.toSet)
  }

  def doWork(inp : Array[String]) : Int = {
    val k = getCombination(createMapForAllWomen(ip1))
    if (k == 0) -1 else k
  }

  def getCombination(inputA: Map[String, Set[String]]) : Int = {
    val (womenTaken, inDemandWomen) = inputA.partition(_._2.size == 1)
    val aloneWomen = inputA.filter(_._2.size == 0)
    val allOccupiedMen = womenTaken.flatMap(_._2).toSet

    if (aloneWomen.size > 0)
      0
    else if (inDemandWomen.size > 0) {
      if (womenTaken.size == 0)
        getHeuristic(inDemandWomen)
       else
        getCombination(inDemandWomen.map(x => x._1 -> (x._2.diff(allOccupiedMen))))

    }
    else
      1
  }

  def getHeuristic(inDemandWomen: Map[String, Set[String]]) : Int = {
    inDemandWomen.foldLeft(0)((a, w) =>
        a + w._2.foldLeft(0)((x , m) =>
          x + getCombination(inDemandWomen.filter(!_._1.equals(w._1)).map(x => x._1 -> (x._2 - m)))
        )
    )
  }
}
