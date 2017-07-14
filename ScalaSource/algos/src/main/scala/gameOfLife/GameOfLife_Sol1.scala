package gameOfLife

object GameOfLife_Sol1 extends App {

  //Still Life
  val blockPattern = List((1,1), (1,2), (2,1), (2,2))
  val boatPattern = List((0, 1), (1, 0), (2, 1), (0, 2), (1, 2))

  //Oscillator
  val blinkerPattern = List((1, 1), (1, 0), (1, 2)) //Answer: List((1,1), (0,1), (2,1))

  //Two Phase Oscillator
  val toadPattern = List((1, 1), (1, 2), (1, 3), (2, 2), (2, 3), (2, 4)) //Answer: List((1,1), (2,4), (0,2), (1,4), (3,3), (2,1))

  //Start
  val initialCells = toadPattern

  def getNeighBors(x : (Int, Int)) : List[(Int,Int)] = {
    List((x._1 - 1, x._2),
    (x._1 , x._2+1),
    (x._1 + 1, x._2),
    (x._1 , x._2 - 1),
    (x._1 - 1, x._2 - 1),
    (x._1 - 1, x._2 + 1),
    (x._1 + 1, x._2 - 1),
    (x._1 + 1, x._2 + 1))
  }

  def getAliveNeighbors(x : (Int, Int)) : List[(Int, Int)] = {
    getNeighBors(x).filter(initialCells.contains(_))
  }

  def getDeadNeighbors(x : (Int, Int)) : List[(Int, Int)] = {
    getNeighBors(x).filter(!initialCells.contains(_))
  }

  def neighborsCheck(x: (Int, Int), f : Int => Boolean) : Boolean = {
    val neighBorsAlive = getAliveNeighbors(x).size
    f(neighBorsAlive)
  }

  def willStayAlive(x: (Int, Int)): Boolean = {
    val isDead = neighborsCheck(x, n => n < 2 || n >  3)
    if (!isDead) {
      neighborsCheck(x, n => n == 2 || n == 3)
    } else {
      false
    }
  }

  def getDeadCells : Set[(Int, Int)] = initialCells.flatMap(getDeadNeighbors(_)).toSet

  def nextTick(initialCells: List[(Int, Int)]) : List[(Int, Int)] = {
    val newCells = for (
      x <- getDeadCells;
      if (neighborsCheck(x , n=> n == 3))
    ) yield x
    initialCells.filter(willStayAlive _) ++ newCells
  }

  val finalCells = nextTick(initialCells)
  println(initialCells)
  println(finalCells)
}
