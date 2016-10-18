package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
 }

  /**
   * Exercise 1
   */
  def pascal(c:Int, r: Int) : Int = {
    def pascalGenerate(c:Int, r:Int) : Int = {
      if (c <=r) {
        if (r == 0) 1 else {
          if (c == 0) 1 else
            pascalGenerate(c-1, r-1) + pascalGenerate(c, r-1)
        }
      } else 0
    }

    pascalGenerate(c,r)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def startBalance(o : Int, c: Int, chars: List[Char]) : Boolean = {
      if (o < c) false
      else {
        if (!chars.isEmpty && chars.head == '(') startBalance(o+1, c, chars.tail)
        else if (!chars.isEmpty && chars.head == ')') startBalance(o, c+1, chars.tail)
        else if (chars.isEmpty)
          if (o == c) true else false
        else
          startBalance(o,c, chars.tail)
      }
    }

    startBalance(0, 0, chars)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    var total = 0
    def startCount(currentCoins : List[Int]) : Unit = {
      var currentSum = sumOfList(currentCoins)
      if (currentSum == money) total += 1
      else if (currentSum < money) {
        coins.foreach(x => {
          if (currentCoins.isEmpty || x >= currentCoins.last)
            startCount(currentCoins :+ x)
        })
      }
    }

    def sumOfList(numList : List[Int]) : Int = {
      var sum= 0
      numList.foreach(s => sum += s)
      sum
    }

    startCount(List())
    total
  }
}
