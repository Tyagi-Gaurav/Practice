/**
 *
 * @author: tyagig
 */
/**
 *
 * @author: tyagig
 */

def abs(x: Double) : Double = if (x >= 0 ) x else (-1 * x)

def sqrt (x : Double) : Double = {
  def calSqrt(guess : Double) : Double = {
    if (isGoodGuess(guess)) guess else calSqrt(improveGuess(guess))
  }

  def isGoodGuess(guess : Double) = {
    abs((guess * guess - x)/x) < 0.001
  }

  def improveGuess(guess : Double) : Double = {
    (guess + x/guess)/2
  }

  calSqrt(1)
}

println(" Square Root of 1E-12 : " + sqrt(1E-12))
println(" Square Root of 2 : " + sqrt(2))
println(" Square Root of 4 : " + sqrt(4))
println(" Square Root of 5 : " + sqrt(5))
println(" Square Root of 30 : " + sqrt(30))
println(" Square Root of 81 : " + sqrt(81))
println(" Square Root of 1E60 : " + sqrt(1E60))

