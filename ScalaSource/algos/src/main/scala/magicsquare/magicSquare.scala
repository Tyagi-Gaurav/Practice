package magicsquare

import scala.io.StdIn

class magicSquare {
  def main(args: Array[String]) {

    val ip1_rows = StdIn.readInt
    val ip1_cols = StdIn.readInt
    val ip1 = Array.ofDim[Int](ip1_rows, ip1_cols)

    for(ip1_i <- 0 until ip1_rows)
      ip1(ip1_i) = scala.io.StdIn.readLine().split(" ").map(_.toInt)


    val output = SolveMagicSquare(ip1);
    println(output)
    println ("Total Memory Used" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()))
  }

  def SolveMagicSquare(input1: Array[Array[Int]]): Int = {
    0
  }
}
