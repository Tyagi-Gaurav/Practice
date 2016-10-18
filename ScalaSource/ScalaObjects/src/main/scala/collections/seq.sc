//When to use lists
// If access patterns are bulk, then use Vector, else use lists.
object test {
	val nums = Vector(1, 2, 3, -88)
	val xs = Array(1, 2, 3, 44)
	xs map (x => 2 * x)
	val s = "Hello World"
	s.filter(x => x.isUpper)
	val r : Range = 1 until 5
	val s1 : Range = 6 to 1 by -2
	s exists (c => c.isUpper)
	s forall (c => c.isUpper)
	val pairs = List(1,2,3) zip s
	pairs.unzip
	s flatMap (c => List('.', c))
	xs.sum
	xs.product
	xs.max
	def combinations(xs : List[Int], ys: List[Int]) : List[(Int,Int)] =
		xs flatMap (x => ys map( y=> (x,y)))
	combinations(List(1,2,3), List(4,5,6))
	def scalarProduct(xs : List[Int], ys: List[Int]) : Int =
		(xs zip ys).map(z => z._1 * z._2).sum

	scalarProduct(List(1,2,3), List(4,5,6))

	def isPrime(x : Int) : Boolean = (2 until x).forall(d => x%d != 0)

	isPrime(5)
	isPrime(7)
	isPrime(9)
	isPrime(6)
}
