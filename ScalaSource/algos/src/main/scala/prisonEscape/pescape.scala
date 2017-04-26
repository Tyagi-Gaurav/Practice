package prisonEscape

object pescape {
 def main(args : Array[String]) = {
   val jump : Int = Integer.parseInt(args(0))
   val slide : Int = Integer.parseInt(args(1))
   val n = Integer.parseInt(args(2))

   val wallHeights = for {
     i <- 1 to n
   } yield Integer.parseInt(args(2+i))

   println (calculateJumps(jump, slide, wallHeights.toArray))
 }

  def calculateJumps(jump : Int,
                     slide : Int,
                     wallHeights : Array[Int]) : Int = {

    val jumps = wallHeights.map(calculate(jump, slide, _, 0))
    jumps.foldLeft(0)((x,y) => x+y)
  }

  def calculate(jump : Int, slide : Int, wallHeight : Int, output : Int) : Int = {
    if (wallHeight <= jump) 1 + output
    else
      calculate(jump, slide, wallHeight - jump + slide, 1 + output)
  }

}
