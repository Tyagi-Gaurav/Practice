/**
 *
 * @author: tyagig
 */

def sum(f: Int => Int) (a: Int, b: Int) : Int = {
  if (a > b) 0
    else f(a) + sum(f) (a+1, b)
}

println("Sum of Numbers from 3 to 5 : " + sum(x => x)(3,5))
println("Sum of Squares of Numbers from 3 to 5 : " + sum(x => x * x)(3 ,5))
println("Sum of Cubes of Numbers from 3 to 5 : " + sum(x => x * x * x)( 3 ,5))
