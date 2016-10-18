package exercise


/**
 *
 * @author: tyagig
 */
object ProductExercise  {
    def main(args : Array[String]) {
      println("Product of 3 to 7 " + product (3,7))
      println("Factorial of 5 " + factorial(5))
    }

    def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zeroFactor: Int)(a: Int, b:Int) : Int = {
      if (a > b) zeroFactor
    else
        combine(f(a), mapReduce(f, combine, zeroFactor)(a+1,b))
     }

    def product(a: Int, b: Int) : Int = mapReduce(x=>x, (x,y) => x * y, 1)(a,b)
      //if (a > b) 1 else f(a) * product (f)(a +1 ,b)

    def factorial(n: Int) : Int = product(1, n)

}
