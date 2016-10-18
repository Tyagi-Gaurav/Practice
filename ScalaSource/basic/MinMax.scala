import scala.io.Source

object basicProg  {
       def min(a : Int, b: Int) = if (a > b) b else a

       def max(a : Int, b:Int) = if (a > b) a else b

       def sum(a : List[Int]) = {
       	   var total = 0
	   a.foreach(x => total += x)
	   total
	}

	def readFromFile(file : String) : Unit = {
	    val source = Source.fromFile(file)
	    for (line <- source.getLines())
	    	println (file + ": " + line + ", width: " + line.trim().length())
	}

	def perform(x : Int, y : Int => Int) : Int = y(x)

       def main(args:Array[String]) {
       	   println ("Min (3,2): " + min(3,2))
	   println ("Max (3,2): " + max(3,2))
	   println ("Sum (1,2,3): " + sum(List(1,2,3)))
	   println ("Reading cite_mini.txt file: ")
	   readFromFile("cite_mini.txt")
	   println ("Increment a number: " + perform(1, x=> x+1))
	   println ("Increment a number: " + perform(3, _+1))
       }
}

