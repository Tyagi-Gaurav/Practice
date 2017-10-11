package ikm

/**
Alice and Bob are playing a new game of stones. There are NN stones placed on the ground, forming a sequence.
The stones are labeled from 1 to N.

Alice and Bob in turns take exactly two consecutive stones on the ground until there are no consecutive stones on the ground.
That is, each player can take stone i and stone i+1, where 1≤i≤N−1. If the number of stone left is odd, Alice wins.
Otherwise, Bob wins.

Assume both Alice and Bob play optimally and Alice plays first, do you know who the winner is?
*/

object ProblemA extends App {

  val stoneCount = getInput

  if (stoneCount % 2 == 0)
    Console.out.print("Bob")
  else
    Console.out.print("Alice")

  def getInput : Long = {
    val line = scala.io.StdIn.readLine()
    line.toLong
  }

}
