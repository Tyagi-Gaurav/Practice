package minCoins

object MinCoins extends App {
  val availDenom = Array(1, 2, 5)

  println ("1 : " + determineDenom(1))
  println ("3 : " + determineDenom(3))
  println ("10 : " + determineDenom(10))
  println ("7 : " + determineDenom(7))
  println ("31 : " + determineDenom(31))
  println ("48 : " + determineDenom(48))

  def determineDenom(amount : Int) : Int = {
    /*
    Define State: nc[i] contains coins needed to make i, such that nc[0] = 0
    */

    val nc = Array.ofDim[Int](amount+1)
    for (i <- 1 to amount)
      nc(i) = Integer.MAX_VALUE

    nc(0) = 0

    for (
      i <- 1 to amount;
      j <- availDenom
    ) {
      if (i >= j && i - j >= 0) {
        nc(i) = math.min(nc(i), nc(i -j) + 1)
      }
    }

//    nc.foreach(println)
    nc(amount)
  }

}
