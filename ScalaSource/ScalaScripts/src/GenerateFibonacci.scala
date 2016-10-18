/**
 *
 * @author: tyagig
 */

 def generateFib(n: Int) : Int = {
    if (n == 1 || n == 2) 1
    else {
      generateFib(n-1) + generateFib(n-2)
    }
  }

for (n <- 1 to 10) {
  print(generateFib(n) + " ")
}
