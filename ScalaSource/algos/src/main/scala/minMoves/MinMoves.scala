package minMoves

import java.util


object MinMoves extends App {
  val x1 = 2
  val y1 = 1
  val x2 = 6
  val y2 = 5

  println (calculate(x1, y1, x2, y2))

  def calculate(x1 : Int, y1 : Int, x2 : Int, y2 : Int) = {
    val pMoves = Array((-1,2), (1,2), (2,1), (2,1),
      (1, -2), (-1,-2), (-2,-1), (-2,1))

    val size = 8
    var moves = Array.ofDim[Int](size, size)
    var nodeList : java.util.List[(Int, Int)] = new util.ArrayList[(Int, Int)]()
    for (
      i <- 0 to size - 1;
      j <- 0 to size - 1
    ) moves(i)(j) = Integer.MAX_VALUE

    moves(x1)(y1) = 0
    var notFound = true
    nodeList.add((x1, y1))

    while (nodeList.size > 0 && notFound) {
      val (a,b) = nodeList.remove(0)
      for (p <- pMoves) {
        val nx = a + p._1
        val ny = b + p._2

        if (nx >= 0 & nx < size && ny >= 0 && ny < size) {
          moves(nx)(ny) = math.min(moves(nx)(ny), 1 + moves(a)(b))

//          println ("[" + nx + "][" + ny + "] = " + moves(nx)(ny))

          if ((nx, ny) == (x2, y2)) {
            notFound = false
          } else {
            nodeList.add((nx, ny))
          }
        }
      }
    }

    moves(x2)(y2)
  }
}
