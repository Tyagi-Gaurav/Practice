abstract class Shape {
  def draw
}

class Rectangle extends Shape {
      def draw : Unit = println("Drawing Rectangle")
}

class Square extends Shape {
      def draw : Unit = println("Drawing Square")
}

object Shape {
       def main(args : Array[String]) {
       	   val s : Shape = new Rectangle
	   println ("Printing...")
	   s.draw
       }
}