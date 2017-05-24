package longestNonDecreasing

/**
  * Given a sequence of N numbers – A[1] , A[2] , …, A[N] . Find the length of the longest non-decreasing sequence.
  */

object NonDecSequen extends App {
  val inputA = Array(1, 5, 3, 7, 9, 11, 8)

  longestNonDecreasingSeq(inputA)

  def longestNonDecreasingSeq(input : Array[Int]) : Array[Int] = {
    val state = Array.ofDim[Int](input.length)
    calculateState(input, state)
    nonDecreasingSequence(input, state)
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

  def nonDecreasingSequence(input : Array[Int], state: Array[Int]) : Array[Int] = {
    (for (
      i <- 0 to state.length - 1;
      if (i == 0 || state(i) > state(i-1))
    ) yield input(i)).toArray
  }
}
