
def scale(xs : List[Double], factor : Double) : List[Double] = xs.map(x => x * factor)


scale(List(1,2,3), 4)

def square(xs: List[Int]) : List[Int] = xs.map(x => x * x)

square(List(1,2,3))

val nums = List(-1,2,3,4,5,6,  -4)
nums.filter(x => x > 0)
nums.filterNot(x => x > 0)

nums.partition(x => x > 0)

//Longest Prefix satisfying the predicate
nums.takeWhile(x => x > 0)
nums.dropWhile(x => x > 0)

nums.span(x => x > 0)

val alphaList = List("a", "a", "a", "b", "c", "c", "a")
def pack[T](xs : List[T]) : List[List[T]] = xs match {
	case Nil => Nil
	case x::xs1 =>
		val (first, rest) = xs.span(y => y == x)
		first::pack(rest)
}

pack(alphaList)

def encode[T](xs: List[T]) : List[(T, Int)] =
	pack(xs) map(ys => (ys.head, ys.length))

encode(alphaList)
val fruits = List("apples", "bananas", "pineapple", "orange")

val queens = Set(List(1, 3, 0, 2), List(2, 0, 3, 1))
for {
	queen <- queens
	col <- 0 until 4
} println(queen + " " + col)







