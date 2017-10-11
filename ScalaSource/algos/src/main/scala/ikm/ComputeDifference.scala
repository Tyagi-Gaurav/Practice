package ikm

/**
  Compute difference of large numbers.
  0 <= a, b <= 10^15
 */
object ComputeDifference extends App {
  var line = scala.io.StdIn.readLine()
  while (line != null) {
    val Array(a, b) = line.split(" ").map(_.toLong)
    // solve test case and output answer
    val r = scala.math.abs(a - b)
    Console.out.println(r)
    line = scala.io.StdIn.readLine()
  }


}
