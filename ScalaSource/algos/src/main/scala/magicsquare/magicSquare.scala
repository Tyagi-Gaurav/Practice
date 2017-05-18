package magicsquare

import scala.io.StdIn

object magicSquare extends App {

//    val ip1_rows = StdIn.readInt
//    val ip1_cols = StdIn.readInt
//    val ip1 = Array.ofDim[Int](ip1_rows, ip1_cols)
//    for(ip1_i <- 0 until ip1_rows)
//      ip1(ip1_i) = scala.io.StdIn.readLine().split(" ").map(_.toInt)
//    val output = SolveMagicSquare(ip1);
//    println(output)

    val potMagcSquare = Array(
          Array(1, 2, 3),
          Array(3, 1, 2),
          Array(2, 3, 1))

    val potMagcSquare1 = Array(
      Array(1, 2, 3),
      Array(3, 1, 2),
      Array(2, 4, 1))

    println (isMagicSquare(potMagcSquare))
    println (isMagicSquare(potMagcSquare1))

  def setBitForNumber(num : Int, bitNum : Int) = num | (0x01 << bitNum - 1)

  def SolveMagicSquare(input1: Array[Array[Int]]): Int = {
    0
  }

  def isMagicSquare(input1: Array[Array[Int]]) : Boolean = {
    val outputSet = input1.map(x =>
      x.foldLeft(0x0)((a, b) => setBitForNumber(a, b))).toSet

    outputSet.size == 1 &&
      outputSet.contains(math.pow(2, input1.size).toInt - 1)
  }
}
