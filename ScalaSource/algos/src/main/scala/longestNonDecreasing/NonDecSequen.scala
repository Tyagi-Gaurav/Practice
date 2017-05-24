package longestNonDecreasing

object NonDecSequen extends App {
  val inputA = Array(1, 5, 3, 7, 9, 11, 8)

  val state = Array.ofDim[Int](inputA.length)

  calculateState(inputA, state)
  longestNonDecreasingSeq(inputA, state)

  def longestNonDecreasingSeq(input : Array[Int], state: Array[Int]) = {
    println ("Longest Non-Decreasing Sequence Length: " + state.max)
    print ("Longest Non-Decreasing Sequence: ")
    printNonDecreasingSeq(input, state)
  }

  def calculateState(input : Array[Int], state: Array[Int]) = {
    state(0) = 1
    for (i <- 1 to input.length - 1) {
      if (input(i) > input(i - 1))
        state(i) = 1 + state(i-1)
      else
        state(i) = state(i-1)
    }
  }

  def printNonDecreasingSeq(input : Array[Int], state: Array[Int]) = {
    print (input(0))
    for (i <- 1 to state.length - 1) {
      if (state(i) > state(i-1))
        print (" " + input(i))
    }
  }
}
