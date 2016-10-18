
package closures

/**
 *
 * @author: tyagig
 */
object Placeholders {
    def sum = (_:Int) + (_:Int)

    def sum3(a : Int, b: Int, c:Int) = a +b + c

    def createIncreaseClosure(more: Int) =  (x: Int) => x + more

    def variableArgumentString(args : String*) = {
       for (arg <- args) println(arg)
    }

    def main(args : Array[String]) = {
      println(sum(2,3))

      /*
      The variable named a refers to a function value object. This function value is an
      instance of a class generated automatically by the Scala compiler from sum _,
      the partially applied function expression. The class generated by the compiler
      has an apply method that takes three arguments.4 The generated class’s apply
      method takes three arguments because three is the number of arguments missing
      in the sum _ expression.
       */
      val a = sum3 _

      println(a(6, 7, 8))

      val add4 = sum3 ( 1, _:Int, 3)
      println(add4(5))

      val inc1 = createIncreaseClosure(1)
      println(inc1(6))
      println(inc1(7))

      val incl99 = createIncreaseClosure(99)
      println(incl99(4))
      println(incl99(7))

      variableArgumentString("Hello")
      variableArgumentString("Hello", "World")

      val stringArray = Array("What's", "up", "Dude")
      //variableArgumentString(stringArray) -- This doesn't work.
      variableArgumentString(stringArray: _*)
    }
}
