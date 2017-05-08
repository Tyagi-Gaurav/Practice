package prisonEscape

object pescape {
 def main(args : Array[String]) = {
//   val jump : Int = Integer.parseInt(args(0))
//   val slide : Int = Integer.parseInt(args(1))
//   val n = Integer.parseInt(args(2))

   val t0 = System.nanoTime()

   val jump = 5
   val slide = 1
   val n = 4

//   val wallHeights = for {
//     i <- 1 to n
//   } yield Integer.parseInt(args(2+i))

   val wallHeights = Seq(5, 7, 4, 8)

   println (calculateJumps(jump, slide, wallHeights.toArray))
   val t1 = System.nanoTime()
   println ("Total Time (In seconds)" + (t1 -t0)/1e9d) //1e9d - 10 to the power 9 as a double
   println ("Total Memory Used (In kb): " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024)
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
