val x = Array("Tom", "Fred", "Harry")
val y = Map("Tom" -> 3, "Dick" -> 4, "Harry" -> 5)

def f(x : Int) = List(x)

x.flatMap(a => y.get(a))

//Add all digits of a nuumber
val b = Array(1,2,3,4,5,7)
b.foldLeft(0)(_ + _)


val c = List(1,2,3,4,5,7)
//Reverse a list
def reverse(c : List[Int]) : List[Int] = {
  c match {
    case x::xs => reverse(xs) :+ x
    case Nil => List.empty[Int]
  }
}

reverse(c)
